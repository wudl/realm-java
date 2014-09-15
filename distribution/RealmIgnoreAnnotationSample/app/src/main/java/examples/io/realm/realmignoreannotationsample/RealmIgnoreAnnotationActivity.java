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

package examples.io.realm.realmignoreannotationsample;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;

import examples.io.realm.realmignoreannotationsample.model.Book;
import io.realm.Realm;
import io.realm.RealmList;


public class RealmIgnoreAnnotationActivity extends Activity {

    public static final String TAG = RealmIgnoreAnnotationActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_realm_ignore_annotation);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.realm_ignore_annotation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void runTest(View v)
    {
        Realm realm = null;

        // Setup the Realm

        try {
            realm = new Realm(getFilesDir());

        } catch (IOException ioe) {
            Log.e(TAG, "Creation of Realm failed");
        }

        realm.clear();
        realm.beginWrite();

        // Fill books in the library:

        Book book = realm.create(Book.class);
        book.setAuthor("John Steinbeck");
        book.setTitle("Of Mice and Men");
        book.setISBN(140177396);
        book.setShelfId("Shelf: 110/C");

        book = realm.create(Book.class);
        book.setAuthor("John Steinbeck");
        book.setTitle("The Grapes Of Wrath");
        book.setISBN(0230031056);
        book.setShelfId("Shelf: 110/C");

        book = realm.create(Book.class);
        book.setAuthor("John Steinbeck");
        book.setTitle("East of Eden ");
        book.setISBN(0142004235);
        book.setShelfId("Shelf: 110/C");

        realm.commit();

        // Read the books back from the Realm:
        RealmList<Book> bookList = realm.where(Book.class).findAll();
        int sizeBookList = bookList.size();

        String libraryBooks = "";

        for (int i = 0; i < sizeBookList; i++) {
            Book readBook = null;
            readBook = bookList.get(i);
            libraryBooks += "\nBook: " + readBook.getTitle() + " Author: " +
                    readBook.getAuthor()+" ISBN: " + readBook.getISBN().toString() +
                    " Shelf: " + readBook.getShelfId();
        }

        TextView resultView = (TextView)findViewById(R.id.resultView);
        resultView.setText(libraryBooks);

    }

    public void exitTest(View v) {
        this.finish();
    }
}
