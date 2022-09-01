package ru.axtane.springMVC.models;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private int book_id;
    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "person_id")
    private Person owner;
    @Column(name = "name")
    private String name;
    @Column(name = "author")
    private String author;
    @Column(name = "yearofwriting")
    private int yearOfWriting;
    @Column(name = "rentalTime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date rentalTime;
    @Transient
    private boolean expired;

    public Book(String name, String author, int yearOfWriting) {
        this.name = name;
        this.author = author;
        this.yearOfWriting = yearOfWriting;
    }

    public Book() {
    }

    public int getBook_id() {
        return book_id;
    }

    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYearOfWriting() {
        return yearOfWriting;
    }

    public void setYearOfWriting(int yearOfWriting) {
        this.yearOfWriting = yearOfWriting;
    }

    public Date getRentalTime() {
        return rentalTime;
    }

    public void setRentalTime(Date rentalTime) {
        this.rentalTime = rentalTime;
    }

    public boolean isExpired() {
        return expired;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }
}
