package examples.io.realm.realmignoreannotationsample;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.IOException;
import java.util.ArrayList;

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
        try {
            Realm realm = new Realm(getFilesDir());

            realm.clear();
            realm.beginWrite();

            // Fill books in the library:

            Book book1 = realm.create(Book.class);
            book1.setAuthor("John Steinbeck");
            book1.setTitle("Of Mice and Men");
            book1.setISBN(140177396);
            book1.setShelfId("Shelf: 110/C");

            Book book2 = realm.create(Book.class);
            book2.setAuthor("John Steinbeck");
            book2.setTitle("The Grapes Of Wrath");
            book2.setISBN(0230031056);
            book2.setShelfId("Shelf: 110/C");

            Book book3 = realm.create(Book.class);
            book3.setAuthor("John Steinbeck");
            book3.setTitle("East of Eden ");
            book3.setISBN(0142004235);
            book3.setShelfId("Shelf: 110/C");

            realm.commit();

            RealmList<Book> bookList = realm.where(Book.class).findAll();
            int sizeBookList = bookList.size();
            final ListView listview = (ListView) findViewById(R.id.listView);
            final ArrayList<String> list = new ArrayList<String>();


            for (int i = 0; i < sizeBookList; i++) {
                Book readBook = bookList.get(i);
                readBook.setShelfId("Undefined");
                    list.add("Book: " + readBook.getTitle() + " Author " +
                            readBook.getAuthor()+" Author " + readBook.getISBN().toString() +
                            " Shelf: " + readBook.getShelfId());
            }

            final ArrayAdapter<String> adapter = new ArrayAdapter(this,
                    R.id.listView, list);
            listview.setAdapter(adapter);

        } catch (IOException ioe) {
            Log.e(TAG, "Realm failed to create and read the book list");
        }
    }


}
