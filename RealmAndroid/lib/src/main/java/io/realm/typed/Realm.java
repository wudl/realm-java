package io.realm.typed;

import com.google.dexmaker.stock.ProxyBuilder;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import io.realm.ColumnType;
import io.realm.ImplicitTransaction;
import io.realm.LinkView;
import io.realm.Row;
import io.realm.SharedGroup;
import io.realm.Table;


public class Realm {

    private static SharedGroup.Durability defaultDurability = SharedGroup.Durability.FULL;

    private SharedGroup sg;
    private ImplicitTransaction transaction;
    private String filePath;
    private int version;
    private File bytecodeCache;
    private ScheduledExecutorService ses = Executors.newSingleThreadScheduledExecutor();

    private List<RealmChangeListener> changeListeners;
    boolean runEventHandler = false;

    public Realm(File writeablePath) throws IOException {
        this(writeablePath, "default.realm");
    }

    public Realm(File writeablePath, String filePath) throws IOException {
        this.filePath = new File(writeablePath, filePath).getAbsolutePath();
        File bytecodeCache = new File(writeablePath, "dx");
        if (!bytecodeCache.exists()) {
            boolean success = bytecodeCache.mkdirs();
            if (!success) {
                throw new IOException("Could not create the bytecode cache folder");
            }
        }

        this.bytecodeCache = bytecodeCache;
        this.changeListeners = new ArrayList<RealmChangeListener>();
        init();
    }

    private void startEventHandler() {
        runEventHandler = true;
        RealmEventHandler realmEventHandler = new RealmEventHandler(this);
        ses.scheduleWithFixedDelay(realmEventHandler, 0, 100, TimeUnit.MILLISECONDS);
    }

    @Override
    protected void finalize() throws Throwable {
        transaction.endRead();
        System.out.println("finalize");
        super.finalize();
    }

    private void init() {
        this.sg = new SharedGroup(filePath, defaultDurability);
        this.transaction = sg.beginImplicitTransaction();
    }

    public static void setDefaultDurability(SharedGroup.Durability durability) {
        defaultDurability = durability;
    }

    public Table getTable(String name) {
        return transaction.getTable(name);
    }

    ColumnType columnTypeFromInt(int value)
    {
        switch (value)
        {
            case 1: return ColumnType.BOOLEAN;
            case 0: return ColumnType.INTEGER;
            case 9: return ColumnType.FLOAT;
            case 10: return ColumnType.DOUBLE;
            case 2: return ColumnType.STRING;
            case 4: return ColumnType.BINARY;
            case 7: return ColumnType.DATE;
            case 8: return ColumnType.TABLE;
            case 6: return ColumnType.MIXED;
            case 12: return ColumnType.LINK;
            case 13: return ColumnType.LINK_LIST;
            default: return ColumnType.INTEGER;
        }
    }
    private <E extends RealmObject> void initTable(E object) {

        // Check for table existence
        String t_name = object.getTableName();
        if(!transaction.hasTable(t_name)) {
            // Create the table
            Table table = transaction.getTable(t_name);

            String[] tableNames = object.getTableRowNames();

            int[] tableTypes = object.getTableRowTypes();

            for (int i = 0; i < tableNames.length; i++) {
                table.addColumn(columnTypeFromInt(tableTypes[i]), tableNames[i].toLowerCase(Locale.getDefault()));
            }

        }

    }


    /**
     * Instantiates and adds a new object to the realm
     *
     * @return              The new object
     * @param <E>
     */
    public <E extends RealmObject> E create(E object) {

        try {
            initTable(object);

            Table table = getTable(object.getTableName());

            long rowIndex = table.addEmptyRow();

            return get(object.getTableName(), object, rowIndex);
        }
        catch (Exception ex)
        {
            System.out.print("Realm.create has failed: "+ex.getMessage());
        }
        return null;
    }

    public <E> void remove(String tableName, long objectIndex) {
        getTable(tableName).moveLastOver(objectIndex);
    }

    private Map<String, List<Field>> cache = new HashMap<String, List<Field>>();

    /**
     * Adds an object to the realm, and returns a new instance which is backed by the Realm
     *
     * @param element           The element to add to this realm.
     * @param <E>
     * @return
     */
    public <E extends RealmObject> void add(E element) {
        throw new NoSuchMethodError("the add method should not be called and is not implemented");
    }

    <E extends RealmObject> E get(String simpleName, E obj , long rowIndex) {
        Row row = transaction.getTable(simpleName).getRow(rowIndex);
        obj.realmSetRow(row);
        return obj;
    }

    public boolean contains(String clazzName) { return transaction.hasTable(clazzName);}

    /**
     * Returns a typed RealmQuery, which can be used to query for specific objects of this type
     *
     * @param clazz         The class of the object which is to be queried for
     * @param <E extends RealmObject>
     * @return
     */
    public <E extends RealmObject> RealmQuery<E> where(String clazzName) {
        return new RealmQuery<E>(this, clazzName);
    }


    public <E extends RealmObject> RealmTableOrViewList<E> allObjects(String clazzName) {
        return (RealmTableOrViewList<E>)where(clazzName).findAll();
    }


    // Migration

    public void ensureRealmAtVersion(int version, RealmMigration migration) {
        migration.execute(this, version);
    }

    // Notifications

    public void addChangeListener(RealmChangeListener listener) {
        changeListeners.add(listener);
        if(!runEventHandler) {
            startEventHandler();
        }
    }

    public void removeChangeListener(RealmChangeListener listener) {
        changeListeners.remove(listener);
        if(runEventHandler && changeListeners.isEmpty()) {
            runEventHandler = false;
        }
    }

    public void removeAllChangeListeners() {
        changeListeners.clear();
    }

    void sendNotifications() {
        for(RealmChangeListener listener : changeListeners) {
            listener.onChange();
        }
        if(runEventHandler && changeListeners.isEmpty()) {
            runEventHandler = false;
        }
    }

    boolean hasChanged() {
        return sg.hasChanged();
    }

    // Transactions

    public void refresh() {
        transaction.advanceRead();
    }

    /**
     * Starts a write transaction, this must be closed with either commit() or rollback()
     */
    public void beginWrite() {

        // If we are moving the transaction forward, send local notifications
        if (sg.hasChanged()) {
            sendNotifications();
        }

        transaction.promoteToWrite();
    }

    /**
     * Commits a write transaction
     */
    public void commit() {
        transaction.commitAndContinueAsRead();

        // Send notifications because we did a local change
        sendNotifications();

    }

    public void clear(String name) {
        getTable(name).clear();
    }

    public void clear() {
        transaction.endRead();
        sg.close();
        new File(filePath).delete();
        new File(filePath+".lock").delete();
        init();
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    private File getBytecodeCache() {
        return bytecodeCache;
    }

}
