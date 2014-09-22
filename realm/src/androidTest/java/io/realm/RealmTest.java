/*
 * Copyright 2014 Realm Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.realm;

import android.content.Context;

import java.io.IOException;
import java.util.concurrent.Semaphore;

import io.realm.entities.AllTypes;
import io.realm.entities.Dog;
import io.realm.internal.SharedGroup;
import io.realm.internal.Table;


public class RealmTest extends RealmSetupTests {

    private final Semaphore asyncReaderDone = new Semaphore(1, true);
    private final static int BACKGROUND_COMMIT_TEST_DATA_SET_SIZE = 5;


    // Test io.realm.Realm API

    // Realm Constructors
    public void testShouldCreateRealm() {
        try {
            Realm realm = new Realm(getContext());
        } catch (Exception ex) {
            fail("Unexpected Exception: "+ex.getMessage());
        }
    }

    public void testShouldNotFailCreateRealmWithNullContext() {
        Context c = null;

        Realm realm = new Realm(c);
        fail("Realm has been created with null Context");
    }

    // setDefaultDurability(SharedGroup.Durability durability)
    public void testShouldSetDurabilityFull() {
        Realm.setDefaultDurability(SharedGroup.Durability.FULL);
        //TODO Add code that checks that the DefaultDurability has been set
    }

    // setDefaultDurability(SharedGroup.Durability durability)
    public void testShouldSetDurabilityFullByName() {
        Realm.setDefaultDurability(SharedGroup.Durability.valueOf("FULL"));
        //TODO Add code that checks that the DefaultDurability has been set
    }

    // setDefaultDurability(SharedGroup.Durability durability)
    public void testShouldNotFailSettingInvalidDurability() {
        Realm.setDefaultDurability(SharedGroup.Durability.valueOf("INVALID"));
        //TODO Add code that checks that the DefaultDurability has NOT been set
    }

    // setDefaultDurability(SharedGroup.Durability durability)
    public void testShouldSetDurabilityMemOnly() {
        Realm.setDefaultDurability(SharedGroup.Durability.MEM_ONLY);
        //TODO Add code that checks that the DefaultDurability has been set
    }

    // Table getTable(Class<?> clazz)
    public void testShouldGetTable() {
        testRealm.beginWrite();

        Table table = testRealm.getTable(AllTypes.class);
        testRealm.commit();
        assertNotNull("getTable is returning a null Table object", table);
    }

    // <E> void remove(Class<E> clazz, long objectIndex)
    public void testShouldRemoveRow() {
        testRealm.beginWrite();

        testRealm.remove(AllTypes.class, 0);

        testRealm.commit();
        RealmResults<AllTypes> resultList = testRealm.where(AllTypes.class).findAll();
        assertEquals("Realm.delete has not deleted record correctly", TEST_DATA_SIZE - 1, resultList.size());

    }

    // <E extends RealmObject> E get(Class<E> clazz, long rowIndex)
    public void testShouldGetObject() {

        RealmObject allTypes = testRealm.get(AllTypes.class,0);
        assertNotNull("get has returned null object", allTypes);
    }

    // boolean contains(Class<?> clazz)
    public void testShouldContainTable() {

        testRealm.beginWrite();
        AllTypes allTypes = testRealm.create(AllTypes.class);
        testRealm.commit();
        assertTrue("contains returns false for newly created table", testRealm.contains(AllTypes.class));
    }

    // boolean contains(Class<?> clazz)
    public void testShouldNotContainTable() {

        assertFalse("contains returns true for non-existing table", testRealm.contains(Dog.class));
    }

    // <E extends RealmObject> RealmQuery<E> where(Class<E> clazz)
    public void testShouldReturnResultSet()  {

        RealmResults<AllTypes> resultList = testRealm.where(AllTypes.class).findAll();

        assertEquals("Realm.get is returning wrong number of objects", TEST_DATA_SIZE, resultList.size());
    }

    // Note that this test is relying on the values set while initializing the test dataset
    public void testQueriesResults() throws IOException {
        RealmResults<AllTypes> resultList = testRealm.where(AllTypes.class).equalTo("columnlong",33).findAll();
        assertEquals("ResultList.where not returning expected result", 1, resultList.size());

        resultList = testRealm.where(AllTypes.class).equalTo("columnlong",3333).findAll();
        assertEquals("ResultList.where not returning expected result", 0, resultList.size());


    }

    public void testQueriesFailWithWrongDataTypes() throws IOException {
        RealmResults<AllTypes> resultList = null;

        try { resultList = testRealm.where(AllTypes.class).equalTo("columnstring",3333).findAll(); fail("Realm.where should fail with illegal argument"); } catch (IllegalArgumentException e) { }
        try { resultList = testRealm.where(AllTypes.class).equalTo("columnstring",true).findAll(); fail("Realm.where should fail with illegal argument"); } catch (IllegalArgumentException e) { }
        try { resultList = testRealm.where(AllTypes.class).equalTo("columnstring",3.1415f).findAll(); fail("Realm.where should fail with illegal argument"); } catch (IllegalArgumentException e) { }
        try { resultList = testRealm.where(AllTypes.class).equalTo("columnstring",3.1415d).findAll(); fail("Realm.where should fail with illegal argument"); } catch (IllegalArgumentException e) { }

        try { resultList = testRealm.where(AllTypes.class).equalTo("columnfloat",12).findAll(); fail("Realm.where should fail with illegal argument"); } catch (IllegalArgumentException e) { }
        try { resultList = testRealm.where(AllTypes.class).equalTo("columnfloat",true).findAll(); fail("Realm.where should fail with illegal argument"); } catch (IllegalArgumentException e) { }
        try { resultList = testRealm.where(AllTypes.class).equalTo("columnfloat","string").findAll(); fail("Realm.where should fail with illegal argument"); } catch (IllegalArgumentException e) { }
        try { resultList = testRealm.where(AllTypes.class).equalTo("columnfloat", 3.1415d).findAll(); fail("Realm.where should fail with illegal argument"); } catch (IllegalArgumentException e) { }

        try { resultList = testRealm.where(AllTypes.class).equalTo("columnlong",true).findAll(); fail("Realm.where should fail with illegal argument"); } catch (IllegalArgumentException e) { }
        try { resultList = testRealm.where(AllTypes.class).equalTo("columnlong","string").findAll(); fail("Realm.where should fail with illegal argument"); } catch (IllegalArgumentException e) { }
        try { resultList = testRealm.where(AllTypes.class).equalTo("columnlong",3.1415f).findAll(); fail("Realm.where should fail with illegal argument"); } catch (IllegalArgumentException e) { }
        try { resultList = testRealm.where(AllTypes.class).equalTo("columnlong",3.1415d).findAll(); fail("Realm.where should fail with illegal argument"); } catch (IllegalArgumentException e) { }

        try { resultList = testRealm.where(AllTypes.class).equalTo("columnboolean","test").findAll(); fail("Realm.where should fail with illegal argument"); } catch (IllegalArgumentException e) { }
        try { resultList = testRealm.where(AllTypes.class).equalTo("columnboolean",7).findAll(); fail("Realm.where should fail with illegal argument"); } catch (IllegalArgumentException e) { }
        try { resultList = testRealm.where(AllTypes.class).equalTo("columnboolean",3.1415f).findAll(); fail("Realm.where should fail with illegal argument"); } catch (IllegalArgumentException e) { }
        try { resultList = testRealm.where(AllTypes.class).equalTo("columnboolean",3.1415d).findAll(); fail("Realm.where should fail with illegal argument"); } catch (IllegalArgumentException e) { }

        try { resultList = testRealm.where(AllTypes.class).equalTo("columnbinary","test").findAll(); fail("Realm.where should fail with illegal argument"); } catch (IllegalArgumentException e) { }
        try { resultList = testRealm.where(AllTypes.class).equalTo("columnbinary",7).findAll(); fail("Realm.where should fail with illegal argument"); } catch (IllegalArgumentException e) { }
        try { resultList = testRealm.where(AllTypes.class).equalTo("columnbinary",3.1415f).findAll(); fail("Realm.where should fail with illegal argument"); } catch (IllegalArgumentException e) { }
        try { resultList = testRealm.where(AllTypes.class).equalTo("columnbinary",3.1415d).findAll(); fail("Realm.where should fail with illegal argument"); } catch (IllegalArgumentException e) { }
    }

    public void testQueriesFailWithInvalidDataTypes() throws IOException {
        RealmResults<AllTypes> resultList = null;

        resultList = testRealm.where(AllTypes.class).equalTo("invalidcolumnname",33).findAll();
        assertNotNull("ResultList.where.equalTo returns null when column name is non-existing", resultList);
        assertEquals("ResultList.where.equalTo returns wrong result", 0, resultList.size());

        resultList = testRealm.where(AllTypes.class).equalTo("invalidcolumnname","test").findAll();
        assertNotNull("ResultList.where.equalTo returns null when column name is non-existing", resultList);
        assertEquals("ResultList.where.equalTo returns wrong result", 0, resultList.size());

        resultList = testRealm.where(AllTypes.class).equalTo("invalidcolumnname",true).findAll();
        assertNotNull("ResultList.where.equalTo returns null when column name is non-existing", resultList);
        assertEquals("ResultList.where.equalTo returns wrong result", 0, resultList.size());

        resultList = testRealm.where(AllTypes.class).equalTo("invalidcolumnname",3.1415d).findAll();
        assertNotNull("ResultList.where.equalTo returns null when column name is non-existing", resultList);
        assertEquals("ResultList.where.equalTo returns wrong result", 0, resultList.size());

        resultList = testRealm.where(AllTypes.class).equalTo("invalidcolumnname",3.1415f).findAll();
        assertNotNull("ResultList.where.equalTo returns null when column name is non-existing", resultList);
        assertEquals("ResultList.where.equalTo returns wrong result", 0, resultList.size());
    }

    public void testQueriesFailWithNullQueryValue() throws IOException {
        RealmResults<AllTypes> resultList = null;

        String nullString = null;
        Float nullFloat = null;
        Long nullLong = null;
        Boolean nullBoolean = null;

        try { resultList = testRealm.where(AllTypes.class).equalTo("columnstring", nullString).findAll(); fail("Realm.where should fail with illegal argument"); } catch (IllegalArgumentException e) { }
        try { resultList = testRealm.where(AllTypes.class).equalTo("columnlong", nullLong).findAll(); fail("Realm.where should fail with illegal argument"); } catch (IllegalArgumentException e) { }
        try { resultList = testRealm.where(AllTypes.class).equalTo("columnboolean", nullBoolean).findAll(); fail("Realm.where should fail with illegal argument"); } catch (IllegalArgumentException e) { }
        try { resultList = testRealm.where(AllTypes.class).equalTo("columnfloat", nullFloat).findAll(); fail("Realm.where should fail with illegal argument"); } catch (IllegalArgumentException e) { }
    }

    // <E extends RealmObject> RealmTableOrViewList<E> allObjects(Class<E> clazz)
    public void testShouldReturnTableOrViewList() {
        testRealm.beginWrite();
        RealmResults<AllTypes> resultList = testRealm.allObjects(AllTypes.class);
        assertEquals("Realm.get is returning wrong result set", TEST_DATA_SIZE, resultList.size());
        testRealm.commit();
    }

    // addChangeListener(RealmChangeListener listener)
    static int testCount = 0;
    public void testChangeNotify() {

        testRealm.addChangeListener(new RealmChangeListener() {
            @Override
            public void onChange() {
                testCount++;
            }
        });

        testRealm.beginWrite();
        for (int i = 0; i < 5; i++) {

            Dog dog = testRealm.create(Dog.class);
            dog.setName("King"+Integer.toString(i) );
        }

        testRealm.commit();

        assertEquals("Have not received the expected number of events in ChangeListener", 1, testCount);

        testRealm.beginWrite();
        for (int i = 0; i < 5; i++) {

            Dog dog = testRealm.create(Dog.class);
            dog.setName("Fido"+Integer.toString(i) );
        }

        testRealm.commit();
        assertEquals("Have not received the expected number of events in ChangeListener", 2, testCount);
    }


    // void removeChangeListener(RealmChangeListener listener)
    public void testChangeNotifyRemove() {

        testCount = 0;

        RealmChangeListener realmChangeListener = new RealmChangeListener() {
            @Override
            public void onChange() {
                testCount++;
            }
        };

        testRealm.addChangeListener(realmChangeListener);

        testRealm.beginWrite();
        for (int i = 0; i < 5; i++) {

            Dog dog = testRealm.create(Dog.class);
            dog.setName("King"+Integer.toString(i) );
        }

        testRealm.commit();
        assertEquals("Have not received the expected number of events in ChangeListener", 1, testCount);


        testRealm.removeChangeListener(realmChangeListener);

        testCount = 0;

        testRealm.beginWrite();
        for (int i = 0; i < 5; i++) {

            Dog dog = testRealm.create(Dog.class);
            dog.setName("Fido"+Integer.toString(i) );
        }

        testRealm.commit();
        assertEquals("Should not receive change notifications after removeChangeListener", 0, testCount);
    }

    // void removeChangeListener(RealmChangeListener listener)
    public void testFailChangeNotifyNoListener() {

        // Invalid input parameter to removeChangeListener:
        testRealm.removeChangeListener(null);

        RealmChangeListener realmChangeListener = new RealmChangeListener() {
            @Override
            public void onChange() {
                testCount++;
            }
        };

        // Invalid input parameter, realmChangeListener has never been added:
        testRealm.removeChangeListener(realmChangeListener);


        // Check that change listeners are still functional:
        testRealm.addChangeListener(realmChangeListener);

        testCount = 0;

        testRealm.beginWrite();
        for (int i = 0; i < 5; i++) {

            Dog dog = testRealm.create(Dog.class);
            dog.setName("Fido"+Integer.toString(i) );
        }

        testRealm.commit();

        assertTrue("Should receive change notifications after adding addChangeListener", 0 < testCount);
    }

    // void removeAllChangeListeners()
    public void testRemoveAllChangeListeners() {

        RealmChangeListener realmChangeListener = new RealmChangeListener() {
            @Override
            public void onChange() {
                testCount++;
            }
        };

        testRealm.addChangeListener(realmChangeListener);

        testRealm.removeAllChangeListeners();


        testCount = 0;

        testRealm.beginWrite();
        for (int i = 0; i < 5; i++) {

            Dog dog = testRealm.create(Dog.class);
            dog.setName("Fido"+Integer.toString(i) );
        }

        testRealm.commit();
        assertEquals("Should not receive change notifications after removeAllChangeListeners", 0, testCount);

    }

    // void removeAllChangeListeners()
    public void testFailRemoveAllChangeListeners() {

        // Calling removeAllChangeListeners w/o any ChangeListeners installed:
        testRealm.removeAllChangeListeners();

        // Verify that change notification is still working:
        RealmChangeListener realmChangeListener = new RealmChangeListener() {
            @Override
            public void onChange() {
                testCount++;
            }
        };

        testRealm.addChangeListener(realmChangeListener);

        testCount = 0;

        testRealm.beginWrite();
        for (int i = 0; i < 5; i++) {

            Dog dog = testRealm.create(Dog.class);
            dog.setName("Fido"+Integer.toString(i) );
        }

        testRealm.commit();
        assertTrue("Should receive change notifications after removeAllChangeListeners", 0 < testCount);
    }


    // This test verifies that an installed RealmChangeListener
    // is called when changes are made across threads
    public void testChangeUpdateFromOtherThread() {

        testCount = 0;

        RealmChangeListener realmChangeListener = new RealmChangeListener() {
            @Override
            public void onChange() {
                testCount++;
            }
        };

        testRealm.addChangeListener(realmChangeListener);

        try {
            asyncReaderDone.acquire();
        } catch (Exception ex) {
            fail("Unexpected exception during test setup " + ex.getMessage());
        }

        new Thread( new Runnable() {
            @Override
            public void run() {
                Realm localRealm = new Realm(getContext());
                localRealm.beginWrite();
                for (int i = 0; i < BACKGROUND_COMMIT_TEST_DATA_SET_SIZE; i++) {

                    Dog dog = localRealm.create(Dog.class);
                    dog.setName("Fido" + Integer.toString(i));
                }
                localRealm.commit();

                asyncReaderDone.release();
            }
        }).start();

        try {
            asyncReaderDone.acquire();

            // We have to sleep in order to leave time for the
            try {
                Thread.sleep(250);
            } catch (Exception ex) {
                fail("Unexpected exception " + ex.getMessage());
            }
            assertTrue("Should receive change notifications when modifying table in another thread", 0 < testCount);
        } catch (Exception ex) {
            fail("Unexpected exception " + ex.getMessage());
        }
    }

    public void testChangeUpdateFromOtherThread2() {

        testCount = 0;

        RealmChangeListener realmChangeListener = new RealmChangeListener() {
            @Override
            public void onChange() {
                testCount++;
            }
        };

        testRealm.addChangeListener(realmChangeListener);

        try {
            asyncReaderDone.acquire();
        } catch (Exception ex) {
            fail("Unexpected exception during test setup " + ex.getMessage());
        }

        new Thread( new Runnable() {
            @Override
            public void run() {
                Realm localRealm = new Realm(getContext());
                localRealm.beginWrite();
                for (int i = 0; i < BACKGROUND_COMMIT_TEST_DATA_SET_SIZE; i++) {

                    Dog dog = localRealm.create(Dog.class);
                    dog.setName("Fido" + Integer.toString(i));
                }
                localRealm.commit();

                asyncReaderDone.release();
            }
        }).start();

        try {
            asyncReaderDone.acquire();

            // We have to sleep in order to leave time for the
            int lastCount = 0;
            int retries = 8;

            while (testCount < BACKGROUND_COMMIT_TEST_DATA_SET_SIZE && retries-- > 0) {
                try {
                    Thread.sleep(250);
                } catch (Exception ex) {
                    fail("Unexpected exception " + ex.getMessage());
                }
                if (lastCount != testCount) {
                    lastCount = testCount;
                    retries = 8;
                }
            }
            assertTrue("Should receive change notifications when modifying table in another thread " + testCount,  0 < testCount);
        } catch (Exception ex) {
            fail("Unexpected exception " + ex.getMessage());
        }
    }

    //void refresh()
    public void testRefresh() {
        testRealm.refresh();

        //TODO add code that tests the result of refresh
    }

    //void beginWrite()
    public void testBeginWrite() throws IOException {

        testRealm.beginWrite();

        AllTypes allTypes = testRealm.create(AllTypes.class);
        allTypes.setColumnString("Test data");
        testRealm.commit();

        RealmResults<AllTypes> resultList = testRealm.where(AllTypes.class).findAll();
        assertEquals("Change has not been committed", TEST_DATA_SIZE + 1, resultList.size());
    }

    //void commit()
    public void testCommit() {
        testRealm.beginWrite();
        AllTypes allTypes = testRealm.create(AllTypes.class);
        allTypes.setColumnBoolean(true);
        testRealm.commit();

        RealmResults<AllTypes> resultList = testRealm.where(AllTypes.class).findAll();
        assertEquals("Change has not been committed", TEST_DATA_SIZE + 1, resultList.size());
    }

    //void clear(Class<?> classSpec)
    public void testClassClear() {

        testRealm.clear(AllTypes.class);

        RealmResults<AllTypes> resultList = testRealm.where(AllTypes.class).findAll();
        assertEquals("Realm.clear does not empty table", 0, resultList.size());
    }

    //void clear()
    public void testClassClearAllWithTwoTables() {
        testRealm.beginWrite();

        Dog dog = testRealm.create(Dog.class);
        dog.setName("Castro");

        testRealm.commit();

        testRealm.clear();

        boolean allNotGone = testRealm.contains(AllTypes.class) || testRealm.contains(Dog.class);
        assertFalse("Realm.clear does not remove table", allNotGone);
    }

    //int getVersion()
    public void testGetVersion() throws IOException {

        int version = testRealm.getVersion();

        assertTrue("Realm.version returns invalid version number " + Integer.toString(version), version > 0);
    }

    //void setVersion(int version)setVersion(int version)
    public void testSetVersion() {
        int version = 42;

        testRealm.setVersion(version);

        assertEquals("Realm.version has not been set by setVersion", version, testRealm.getVersion());
    }

    public void testShouldFailOutsideTransaction() {

        // These API calls should fail outside a Transaction:
        try { AllTypes aT = testRealm.create(AllTypes.class); fail("Realm.create should fail outside write transaction"); } catch (IllegalStateException e) { }
        try { testRealm.remove(AllTypes.class, 0); fail("Realm.remove should fail outside write transaction"); } catch (IllegalStateException e) { }
    }
}
