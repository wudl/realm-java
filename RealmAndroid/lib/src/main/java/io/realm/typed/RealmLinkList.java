package io.realm.typed;

import java.lang.reflect.Constructor;
import java.util.AbstractList;

import io.realm.LinkView;

public class RealmLinkList<E extends RealmObject> extends AbstractList<E> implements RealmList<E> {

    private Class<E> clazz;
    private LinkView view;
    private Realm realm;

    public RealmLinkList(Class<E> clazz, LinkView view, Realm realm) {
        this.clazz = clazz;
        this.view = view;
        this.realm = realm;
    }




    @Override
    public void add(int location, E object) {
        if(object.realmGetRow() == null) {
            realm.add(object);
            view.add(object.realmAddedAtRowIndex);
        } else {
            view.add(object.realmGetRow().getIndex());
        }
    }

    @Override
    public E set(int location, E object) {
        if(object.realmGetRow() == null) {
            realm.add(object);
            view.set(location, object.realmAddedAtRowIndex);
            return realm.get((Class<E>)object.getClass(), object, object.realmAddedAtRowIndex);
        } else {
            view.set(location, object.realmGetRow().getIndex());
            return object;
        }
    }

    @Override
    public void move(int oldPos, int newPos) {
        view.move(oldPos, newPos);
    }

    @Override
    public void clear() {
        view.clear();
    }

    @Override
    public E remove(int location) {
        view.remove(location);
        return null;
    }

    @Override
    public E get(int i) {
//        String inClass = classSpec.getCanonicalName();
//        String outClass = inClass.substring(0,inClass.lastIndexOf("."))+".autogen"+inClass.substring(inClass.lastIndexOf("."));
        String outClass = this.clazz.getCanonicalName();

        try {
            Class<E> clazz = (Class<E>)Class.forName(outClass);
            Constructor<E> ctor = clazz.getConstructor();
            E object = ctor.newInstance(new Object[]{});
//            TableOrView table = getTable();
//            if(table instanceof TableView) {
//                realm.get(classSpec, object, ((TableView)table).getSourceRowIndex(rowIndex));
//            } else {
//                realm.get(classSpec, object, rowIndex);
//            }
//
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
        if(!view.isEmpty()) {
            return get(0);
        }
        return null;
    }

    @Override
    public E last() {
        if(!view.isEmpty()) {
            return get(size()-1);
        }
        return null;
    }

    @Override
    public int size() {
        return ((Long)view.size()).intValue();
    }

    @Override
    public RealmQuery<E> where() {
        return null;
    }

}
