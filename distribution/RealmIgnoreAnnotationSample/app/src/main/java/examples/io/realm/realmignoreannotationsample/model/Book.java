package examples.io.realm.realmignoreannotationsample.model;


import io.realm.RealmObject;
import io.realm.annotations.*;

@RealmClass
public class Book extends RealmObject {

    private String    title;
    private String    author;
    private Integer   ISBN;
    @Ignore private String nameOfBookShelf;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getISBN() {
        return ISBN;
    }

    public void setISBN(Integer ISBN) {
        this.ISBN = ISBN;
    }

    public String getShelfId() {
        return nameOfBookShelf;
    }

    public void setShelfId(String nameOfBookShelf) {
        this.nameOfBookShelf = nameOfBookShelf;
    }

}
