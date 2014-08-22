package io.realm.tests.typed.entities;

@io.realm.base.RealmClass
public class Dog {

    //private RealmList<User> owners = new RealmArrayList<User>();
    private String name;

    //public RealmList<User> getOwners() {
    //    return owners;
    //}

    //public void setOwners(RealmList<User> owners) {
    //    this.owners = owners;
    //}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
