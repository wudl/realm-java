package io.realm.test;

import java.util.ArrayList;

@io.realm.base.RealmClass
public class Dog  {

    public ArrayList<User> owners = new ArrayList<User>();
    private String name;
    private int age;

    public ArrayList<User> getOwners() {
        return owners;
    }

    public void setOwners(ArrayList<User> owners) {
        this.owners = owners;
    }


}
