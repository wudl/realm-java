package io.realm.tests.typed.entities.autogen;

import io.realm.typed.RealmObject;

public class AllColumns extends RealmObject 
{
    java.util.Date getcolumnDate()
    {
        return realmGetRow().getDate(realmGetRow().getColumnIndex("columnDate"));
    }

    void setcolumnDate(java.util.Date value)
    {
        realmGetRow().setDate(realmGetRow().getColumnIndex("columnDate"), value);
    }

//    io.realm.tests.typed.entities.User getcolumnRealmObject()
//    {
//        return realmGetRow().getUser(realmGetRow().getColumnIndex("columnRealmObject"));
//    }
//
//    void setcolumnRealmObject(io.realm.tests.typed.entities.User value)
//    {
//        realmGetRow().setUser(realmGetRow().getColumnIndex("columnRealmObject"), value);
//    }

    double getcolumnDouble()
    {
        return realmGetRow().getDouble(realmGetRow().getColumnIndex("columnDouble"));
    }

    void setcolumnDouble(double value)
    {
        realmGetRow().setDouble(realmGetRow().getColumnIndex("columnDouble"), value);
    }

    java.lang.String getname()
    {
        return realmGetRow().getString(realmGetRow().getColumnIndex("name"));
    }

    void setname(java.lang.String value)
    {
        realmGetRow().setString(realmGetRow().getColumnIndex("name"), value);
    }

    long getcolumnLong()
    {
        return realmGetRow().getLong(realmGetRow().getColumnIndex("columnLong"));
    }

    void setcolumnLong(long value)
    {
        realmGetRow().setLong(realmGetRow().getColumnIndex("columnLong"), value);
    }

    boolean getcolumnBoolean()
    {
        return realmGetRow().getBoolean(realmGetRow().getColumnIndex("columnBoolean"));
    }

    void setcolumnBoolean(boolean value)
    {
        realmGetRow().setBoolean(realmGetRow().getColumnIndex("columnBoolean"), value);
    }

//    byte getcolumnBinary()
//    {
//        return (Byte)realmGetRow().getLong(realmGetRow().getColumnIndex("columnBinary"));
//    }
//
//    void setcolumnBinary(byte value)
//    {
//        realmGetRow().setLong(realmGetRow().getColumnIndex("columnBinary"), value);
//    }

    java.lang.String getcolumnString()
    {
        return realmGetRow().getString(realmGetRow().getColumnIndex("columnString"));
    }

    void setcolumnString(java.lang.String value)
    {
        realmGetRow().setString(realmGetRow().getColumnIndex("columnString"), value);
    }

    float getcolumnFloat()
    {
        return realmGetRow().getFloat(realmGetRow().getColumnIndex("columnFloat"));
    }

    void setcolumnFloat(float value)
    {
        realmGetRow().setFloat(realmGetRow().getColumnIndex("columnFloat"), value);
    }

}

