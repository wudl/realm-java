package io.realm.typed;


import java.lang.reflect.Constructor;
import java.util.AbstractList;
import java.util.HashMap;
import java.util.Map;

import io.realm.TableOrView;
import io.realm.TableView;

/**
 *
 * @param <E> The class of objects in this list
 */
public class RealmTableOrViewList<E extends RealmObject> extends AbstractList<E> implements RealmList<E> {

    //private Class<E> classSpec;
    private String className;
    private Realm realm;
    private TableOrView table = null;

    RealmTableOrViewList(Realm realm, String className) {
        this.realm = realm;
        this.className = className;
    }

    RealmTableOrViewList(Realm realm, TableOrView table, String className) {
        this(realm, className);
        this.table = table;
    }

    Realm getRealm() {
        return realm;
    }

    TableOrView getTable() {

        if(table == null) {
            return realm.getTable(className);
        } else {
            return table;
        }
    }

    @Override
    public void move(int oldPos, int newPos) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException();
    }

    Map<String, Class<?>> cache = new HashMap<String, Class<?>>();


    @Override
    public RealmQuery<E> where() {
        return new RealmQuery<E>(this, className);
    }


    private E createObject(String className)
    {
        if (className.compareTo("io.realm.tests.typed.entities.autogen.User") == 0)
        {
            return (E)new io.realm.tests.typed.entities.autogen.User();
        }
        else
        {
            return null;
        }
    }

    @Override
    public E get(int rowIndex) {

        String outClass = className.substring(0,className.lastIndexOf("."))+".autogen"+className.substring(className.lastIndexOf("."));
        try {
            E object = createObject(outClass);
            TableOrView table = getTable();
            if(table instanceof TableView) {
                realm.get(className, object, ((TableView)table).getSourceRowIndex(rowIndex));
            } else {
                realm.get(className, object, rowIndex);
            }

            return object;


        }
        catch (Exception ex)
        {
            System.out.print("Realm.create has failed: "+ex.getMessage());
        }

        return null;
    }

    @Override
    public E first() {
        return get(0);
    }

    @Override
    public E last() {
        return get(size()-1);
    }

    // Aggregates


    @Override
    public int size() {
        return ((Long)getTable().size()).intValue();
    }

}
