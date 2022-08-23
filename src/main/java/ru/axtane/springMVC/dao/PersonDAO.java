package ru.axtane.springMVC.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.axtane.springMVC.models.Person;

import java.util.List;
import java.util.Optional;

@Component
public class PersonDAO {
    private final JdbcTemplate jdbcTemplate;
    private final SessionFactory sessionFactory;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate, SessionFactory sessionFactory) {
        this.jdbcTemplate = jdbcTemplate;
        this.sessionFactory = sessionFactory;
    }
    @Transactional(readOnly = true)
    public List<Person> index(){
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select p from Person p", Person.class).getResultList();
        /*return jdbcTemplate.query("SELECT * FROM person", new BeanPropertyRowMapper<>(Person.class));*/
    }
    @Transactional(readOnly = true)
    public Person show(int id){
        Session session = sessionFactory.getCurrentSession();
        return session.get(Person.class, id);
        /*return jdbcTemplate.query("SELECT * FROM person WHERE person_id=?",
                new Object[]{id}, new BeanPropertyRowMapper<>(Person.class)).stream().findAny().orElse(null);*/
    }
    @Transactional(readOnly = true)
    public Optional<Person> show(String fio){
        /*Session session = sessionFactory.getCurrentSession();
        Person person = session.createQuery("from Person where fio=:fio", Person.class).setParameter("fio", fio).getSingleResult();
        return person;*/
        return jdbcTemplate.query("SELECT * FROM person WHERE fio=?",
                new Object[]{fio}, new BeanPropertyRowMapper<>(Person.class)).stream().findAny();
    }
    @Transactional
    public void save(Person person){
        Session session = sessionFactory.getCurrentSession();
        session.persist(person);
        /*jdbcTemplate.update("INSERT INTO person(fio, yearOfBirth) VALUES(?, ?)", person.getFio(), person.getYearOfBirth());*/
    }
    @Transactional
    public void update(int id, Person updatedPerson){
        Session session = sessionFactory.getCurrentSession();
        Person person = session.get(Person.class, id);
        Person.updatePerson(person, updatedPerson);
        /*jdbcTemplate.update("UPDATE person SET fio=?, yearOfBirth=? WHERE person_id=?", person.getFio(), person.getYearOfBirth(), id);*/
    }
    @Transactional
    public void delete(int id){
        Session session = sessionFactory.getCurrentSession();
        session.remove(session.get(Person.class, id));
        /*jdbcTemplate.update("DELETE FROM person WHERE person_id=?", id);*/
    }
}
