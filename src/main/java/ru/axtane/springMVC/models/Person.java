package ru.axtane.springMVC.models;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "person")
public class Person {
    @Id
    @Column(name = "person_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int person_id;

    @NotEmpty(message = "Name should not be empty")
    @Size(min = 5, max = 150, message = "FIO should be between 5 and 150 characters")
    @Pattern(regexp = "([А-ЯЁ][а-яё]+[\\-\\s]?){3}", message = "Your FIO should be in this format: Surname Name Patronymic")
    @Column(name = "fio")
    private String fio;

    @Min(value = 1900, message = "Year of birth can not be lower than 1900")
    @Max(value = 2022, message = "Year of birth can not be higher than 2022")
    @Column(name = "yearofbirth")
    private int yearOfBirth;

    @OneToMany(mappedBy = "owner")
    private List<Book> books = new ArrayList<>();

    public Person(String fio, int yearOfBirth) {
        this.fio = fio;
        this.yearOfBirth = yearOfBirth;
    }

    public Person() {
    }

    public int getPerson_id() {
        return person_id;
    }

    public void setPerson_id(int person_id) {
        this.person_id = person_id;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public int getYearOfBirth() {
        return yearOfBirth;
    }

    public void setYearOfBirth(int yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public void addBook(Book book){
        book.setOwner(this);
        this.books.add(book);
    }

    public static void updatePerson(Person person, Person updatedPerson){
        person.setFio(updatedPerson.getFio());
        person.setYearOfBirth(updatedPerson.getYearOfBirth());
    }
}
