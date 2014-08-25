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

    private Class<E> classSpec;
    //private String className;
    private Realm realm;
    private TableOrView table = null;

//    RealmTableOrViewList(Realm realm, String className) {
//        this.realm = realm;
//        this.className = className;
//    }
//
//    RealmTableOrViewList(Realm realm, TableOrView table, String className) {
//        this(realm, className);
//        this.table = table;
//    }

    RealmTableOrViewList(Realm realm, Class<E> classSpec) {
        this.realm = realm;
        this.classSpec = classSpec;
    }

    RealmTableOrViewList(Realm realm, TableOrView table, Class<E> classSpec) {
        this(realm, classSpec);
        this.table = table;
    }


    Realm getRealm() {
        return realm;
    }

//    TableOrView getTable() {
//
//        if(table == null) {
//            return realm.getTable(className);
//        } else {
//            return table;
//        }
//    }
    TableOrView getTable()
    {
        if(table == null) {
            return realm.getTable(classSpec);
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
//    public RealmQuery<E> where() {
//        return new RealmQuery<E>(this, className);
//    }
    public RealmQuery<E> where() {
        return new RealmQuery<E>(this, classSpec);
    }


    private E createObject(String className)
    {
        return null;
//        if (className.compareTo("io.realm.tests.typed.entities.User_PROXY") == 0)
//        {
//            return (E)new io.realm.tests.typed.entities.User_PROXY();
//        }
//        else
//        {
//            return null;
//        }
    }

    @Override
//    public E get(int rowIndex) {
//
//        String outClass = className.substring(0,className.lastIndexOf("."))+".autogen"+className.substring(className.lastIndexOf("."));
//        try {
//            E object = createObject(outClass);
//            TableOrView table = getTable();
//            if(table instanceof TableView) {
//                realm.get(className, object, ((TableView)table).getSourceRowIndex(rowIndex));
//            } else {
//                realm.get(className, object, rowIndex);
//            }
//
//            return object;
//
//
//        }
//        catch (Exception ex)
//        {
//            System.out.print("Realm.create has failed: "+ex.getMessage());
//        }
//
//        return null;
//    }
    public E get(int rowIndex) {

        E obj;

        TableOrView table = getTable();
        if(table instanceof TableView) {
            obj = realm.get(classSpec, ((TableView)table).getSourceRowIndex(rowIndex));
        } else {
            obj = realm.get(classSpec, rowIndex);
        }

        return obj;
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
