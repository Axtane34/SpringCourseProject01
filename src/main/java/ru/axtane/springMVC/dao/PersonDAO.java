package ru.axtane.springMVC.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.axtane.springMVC.models.Person;

import java.util.List;
import java.util.Optional;

@Component
public class PersonDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> index(){
        return jdbcTemplate.query("SELECT * FROM person", new BeanPropertyRowMapper<>(Person.class));
    }

    public Person show(int id){
        return jdbcTemplate.query("SELECT * FROM person WHERE person_id=?",
                new Object[]{id}, new BeanPropertyRowMapper<>(Person.class)).stream().findAny().orElse(null);
    }

    public Optional<Person> show(String fio){
        return jdbcTemplate.query("SELECT * FROM person WHERE fio=?",
                new Object[]{fio}, new BeanPropertyRowMapper<>(Person.class)).stream().findAny();
    }

    public void save(Person person){
        jdbcTemplate.update("INSERT INTO person(fio, yearOfBirth) VALUES(?, ?)", person.getFio(), person.getYearOfBirth());
    }

    public void update(int id, Person person){
        jdbcTemplate.update("UPDATE person SET fio=?, yearOfBirth=? WHERE person_id=?", person.getFio(), person.getYearOfBirth(), id);
    }

    public void delete(int id){
        jdbcTemplate.update("DELETE FROM person WHERE person_id=?", id);
    }
}
