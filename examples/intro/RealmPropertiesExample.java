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

package io.realm.tests.examples;

import android.test.AndroidTestCase;

import java.io.IOException;

import io.realm.tests.examples.entities.Dog;
import io.realm.tests.examples.entities.Person;
import io.realm.Realm;
import io.realm.RealmList;

public class RealmPropertiesExample extends AndroidTestCase {


    public void testExample() throws IOException {

        // Realms are used to group data together
        Realm realm = new Realm(getContext().getFilesDir());

        // Update your object in a transaction
        realm.beginWrite();
        Dog myDog = realm.create(Dog.class);
        myDog.setName("Rex");
        myDog.setAge(5);
        realm.commit();

	IntProperty age = Person.intProperty("age");
	Dog rex = Dog.find(age.eq(5));
        System.out.println(rex.getName());

	RealmList<Dog> dogs = Dog.where(age.gt(1));
	for(Dog d : dogs) {
        	System.out.println(d.getName());
	}
	//RealmList<Dog> dogs = Dog.where(age.gt(1).and(age.lt(19)));
        System.out.println(rex.getName());

	//RealmList<Dog> dogs  = Dog.where(.eq(rex));
    }

}
