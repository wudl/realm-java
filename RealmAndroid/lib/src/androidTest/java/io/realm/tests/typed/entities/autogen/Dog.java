package io.realm.tests.typed.entities.autogen;

import io.realm.typed.RealmObject;

public class Dog extends RealmObject 
{
    java.lang.String getname()
    {
        return realmGetRow().getString(realmGetRow().getColumnIndex("name"));
    }

    void setname(java.lang.String value)
    {
        realmGetRow().setString(realmGetRow().getColumnIndex("name"), value);
    }

}

