package ru.axtane.springMVC.services;

import org.hibernate.criterion.Example;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.axtane.springMVC.models.Person;
import ru.axtane.springMVC.repositories.PeopleRepository;

import java.util.List;
import java.util.Optional;


@Service
@Transactional(readOnly = true)
public class PeopleService {
    private final PeopleRepository peopleRepository;

    @Autowired
    public PeopleService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    public List<Person> findAll(){
        return peopleRepository.findAll();
    }

    public Person findById(int id){
        return peopleRepository.findById(id).orElse(null);
    }

    public Optional<Person> findByFio(String fio){
        return peopleRepository.findByFio(fio);
        /*EntityManager entityManager = entityManagerFactory.createEntityManager();
        Person person = entityManager.createQuery("from Person where fio=:fio", Person.class).setParameter("fio", fio).getSingleResult();
        return person;*/
        /*return jdbcTemplate.query("SELECT * FROM person WHERE fio=?",
                new Object[]{fio}, new BeanPropertyRowMapper<>(Person.class)).stream().findAny();*/
    }

    @Transactional
    public void save(Person person){
        peopleRepository.save(person);
    }

    @Transactional
    public void update(int id, Person updatedPerson){
        updatedPerson.setPerson_id(id);
        peopleRepository.save(updatedPerson);
    }

    @Transactional
    public void delete(int id){
        peopleRepository.deleteById(id);
    }
}
