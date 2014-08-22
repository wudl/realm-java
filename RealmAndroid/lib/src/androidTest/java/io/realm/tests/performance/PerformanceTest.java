package io.realm.tests.performance;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
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
import io.realm.tests.typed.entities.User;
import io.realm.typed.Realm;
import io.realm.typed.RealmList;

public class PerformanceTest extends AndroidTestCase {

    public void testPerformance() {


        System.out.println("START START START START START START START START START START START START START START START START START");

        final int listSize = 10000;
        long timer;
        Map<String, Long> timings = new HashMap<String, Long>();


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
        try {
            realm.beginWrite();
            for(int i = 0; i < listSize; i++) {
                io.realm.tests.typed.entities.autogen.User user = realm.create(User.class);
                user.setid(i);
                user.setname("John Doe");
                user.setemail("john@doe.com");
                realm.add(user);

            }
            realm.commit();
        } catch(Throwable t) {
            t.printStackTrace();
            fail();
        }

        //timings.put("RealmList_Add", (System.currentTimeMillis() - timer));

//        timer = System.currentTimeMillis();
//        RealmList<User> realmList = realm.where(User.class).findAll();
//        for(int i = 0; i < listSize; i++) {
            // IUser u = realmList.getTest(i, IUser.class);
//            User u = realmList.get(i);
     //       System.out.println(u.getId());

//            u.getId();
//            u.getName();
//            u.getEmail();

//       }
        //timings.put("RealmList_Get", (System.currentTimeMillis() - timer));


        // TightDB dyn

    }

}
