package io.realm.tests.typed.entities.autogen;

import io.realm.typed.RealmObject;

public class User extends RealmObject 
{
    public int getid()
    {
        return (int)realmGetRow().getLong(realmGetRow().getColumnIndex("id"));
    }

    public void setid(int value)
    {
        realmGetRow().setLong(realmGetRow().getColumnIndex("id"), value);
    }

    public java.util.Date getcolumnDate()
    {
        return realmGetRow().getDate(realmGetRow().getColumnIndex("columnDate"));
    }

    public void setcolumnDate(java.util.Date value)
    {
        realmGetRow().setDate(realmGetRow().getColumnIndex("columnDate"), value);
    }

    public java.lang.String getemail()
    {
        return realmGetRow().getString(realmGetRow().getColumnIndex("email"));
    }

    public void setemail(java.lang.String value)
    {
        realmGetRow().setString(realmGetRow().getColumnIndex("email"), value);
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

    public double getcolumnDouble()
    {
        return realmGetRow().getDouble(realmGetRow().getColumnIndex("columnDouble"));
    }

    public void setcolumnDouble(double value)
    {
        realmGetRow().setDouble(realmGetRow().getColumnIndex("columnDouble"), value);
    }

    public java.lang.String getname()
    {
        return realmGetRow().getString(realmGetRow().getColumnIndex("name"));
    }

    public void setname(java.lang.String value)
    {
        realmGetRow().setString(realmGetRow().getColumnIndex("name"), value);
    }

    public long getcolumnLong()
    {
        return realmGetRow().getLong(realmGetRow().getColumnIndex("columnLong"));
    }

    public void setcolumnLong(long value)
    {
        realmGetRow().setLong(realmGetRow().getColumnIndex("columnLong"), value);
    }

    public boolean getcolumnBoolean()
    {
        return realmGetRow().getBoolean(realmGetRow().getColumnIndex("columnBoolean"));
    }

    public void setcolumnBoolean(boolean value)
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
//
    public java.lang.String getcolumnString()
    {
        return realmGetRow().getString(realmGetRow().getColumnIndex("columnString"));
    }

    public void setcolumnString(java.lang.String value)
    {
        realmGetRow().setString(realmGetRow().getColumnIndex("columnString"), value);
    }

    public float getcolumnFloat()
    {
        return realmGetRow().getFloat(realmGetRow().getColumnIndex("columnFloat"));
    }

    public void setcolumnFloat(float value)
    {
        realmGetRow().setFloat(realmGetRow().getColumnIndex("columnFloat"), value);
    }

}

