package ru.axtane.springMVC.models;

import javax.validation.constraints.*;

public class Person {
    private int person_id;

    @NotEmpty(message = "Name should not be empty")
    @Size(min = 5, max = 150, message = "FIO should be between 5 and 150 characters")
    @Pattern(regexp = "([А-ЯЁ][а-яё]+[\\-\\s]?){3}", message = "Your FIO should be in this format: Surname Name Patronymic")
    private String fio;
    @Min(value = 1900, message = "Year of birth can not be lower than 1900")
    @Max(value = 2022, message = "Year of birth can not be higher than 2022")
    private int yearOfBirth;

    public Person(int person_id, String fio, int yearOfBirth) {
        this.person_id = person_id;
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
}
