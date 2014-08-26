package io.realm.tests.performance;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.os.Debug;
import android.test.AndroidTestCase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.realm.ColumnType;
import io.realm.ReadTransaction;
import io.realm.SharedGroup;
import io.realm.Table;
import io.realm.WriteTransaction;
//import io.realm.tests.typed.entities.autogen.User;
import io.realm.tests.typed.entities.User;
import io.realm.typed.Realm;
import io.realm.typed.RealmList;

public class PerformanceTest extends AndroidTestCase {

    public class aUser {
        public int id;
        public String name;
        public String email;
    }

    public void testPerformance() {

        final int listSize = 10000;
        long timer;
        Map<String, Long> timings = new HashMap<String, Long>();

        // ArrayList

        System.out.println("################################ Testing ArrayList");



        List<aUser> arrayList = new ArrayList<aUser>(listSize);

        timer = System.currentTimeMillis();
        for(int i = 0; i < listSize; i++) {
            aUser user = new aUser();

            user.id = i;
            user.name = "John Doe";
            user.email = "john@doe.com";

            arrayList.add(user);
        }
        timings.put("ArrayList_Add", (System.currentTimeMillis() - timer));

        timer = System.currentTimeMillis();
        for(int i = 0; i < listSize; i++) {
            aUser u = arrayList.get(i);
        }
        timings.put("ArrayList_Get", (System.currentTimeMillis() - timer));


        // RealmList

        System.out.println("################################ Testing new interface");



        Realm.setDefaultDurability(SharedGroup.Durability.FULL);
        Realm realm = null;
        try {
            realm = new Realm(getContext().getFilesDir());
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        realm.clear();

        timer = System.currentTimeMillis();
        //Debug.startMethodTracing("write");
        try {
            realm.beginWrite();
            for(int i = 0; i < listSize; i++) {
                io.realm.tests.typed.entities.User user = realm.create(io.realm.tests.typed.entities.User.class);
                user.setId(i);
                user.setName("John Doe");
                user.setEmail("john@doe.com");
                //realm.add(user);

            }
            realm.commit();
        } catch(Throwable t) {
            t.printStackTrace();
            fail();
        }

        //Debug.stopMethodTracing();

        //System.out.println("RealmList_Add: "+((System.currentTimeMillis() - timer)));
        timings.put("RealmList_Add", (System.currentTimeMillis() - timer));

        timer = System.currentTimeMillis();

        //RealmList<User> realmList = realm.where(io.realm.tests.typed.entities.autogen.User.implName).findAll();
//        for(int i = 0; i < listSize; i++) {
            // IUser u = realmList.getTest(i, IUser.class);
//            User u = realmList.get(i);
     //       System.out.println(u.getId());

//            u.getId();
//            u.getName();
//            u.getEmail();

//       }
        //timings.put("RealmList_Get", (System.currentTimeMillis() - timer));

        // SQLite

        System.out.println("################################ Testing SQLite");

        SQLiteOpenHelper sqLiteOpenHelper = new SQLiteHelper(getContext());
        SQLiteDatabase database = sqLiteOpenHelper.getWritableDatabase();

        database.execSQL("DELETE FROM t1 WHERE 1=1");

        timer = System.currentTimeMillis();


        SQLiteStatement stmt = database.compileStatement("INSERT INTO t1 VALUES(?1, ?2, ?3)");
        database.beginTransaction();
        for (int i = 0; i < listSize; ++i) {
            stmt.clearBindings();
            stmt.bindLong(1, i);
            stmt.bindString(2, "John Doe");
            stmt.bindString(3, "john@doe.com");
            stmt.executeInsert();
        }
        database.setTransactionSuccessful();
        database.endTransaction();
        stmt.close();

        timings.put("SQLite_Add", (System.currentTimeMillis() - timer));
        timer = System.currentTimeMillis();


        Cursor cursor = database.rawQuery(
                String.format("SELECT * FROM t1"),
                null);
        int i = 0;
        if(cursor.moveToFirst()) {
            do {
                aUser user = new aUser();
                user.id = cursor.getInt(0);
                user.name = cursor.getString(1);
                user.email = cursor.getString(2);
                i++;
            } while(cursor.moveToNext());
        }

        timings.put("SQLite_Get", (System.currentTimeMillis() - timer));
        System.out.println("SQL ROWS " + i);

        // Output results
        System.out.println("New Interface:");
        System.out.println("Add: " + timings.get("RealmList_Add")+" ms");
//        System.out.println("Get: " + timings.get("RealmList_Get")+" ms");

        System.out.println("ArrayList Interface:");
        System.out.println("Add: " + timings.get("ArrayList_Add") + " ms\t\t(x" + (timings.get("RealmList_Add").doubleValue() / timings.get("ArrayList_Add").doubleValue()) + ")");
//        System.out.println("Get: " + timings.get("ArrayList_Get") + " ms\t\t(x" + (timings.get("RealmList_Get").doubleValue() / timings.get("ArrayList_Get").doubleValue()) + ")");

        System.out.println("SQLite:");
        System.out.println("Add: " + timings.get("SQLite_Add") + " ms\t\t(x" + (timings.get("RealmList_Add").doubleValue() / timings.get("SQLite_Add").doubleValue()) + ")");
//        System.out.println("Get: " + timings.get("SQLite_Get") + " ms\t\t(x" + (timings.get("RealmList_Get").doubleValue() / timings.get("SQLite_Get").doubleValue()) + ")");

    }

}
