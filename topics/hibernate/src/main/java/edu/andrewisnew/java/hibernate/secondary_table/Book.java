package edu.andrewisnew.java.hibernate.secondary_table;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SecondaryTable;

//book text будет храниться в отдельной таблице
@Entity
@SecondaryTable(
        name = "BOOK_TEXT",
        pkJoinColumns = @PrimaryKeyJoinColumn(name = "BOOK_ID")
)
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;
    private String name;
    @Column(table = "BOOK_TEXT")
    private String text;

    public Book(String name, String text) {
        this.name = name;
        this.text = text;
    }

    public Book() {
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
