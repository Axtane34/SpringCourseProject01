package ru.axtane.springMVC.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.axtane.springMVC.models.Person;

@Repository
public interface PeopleRepository extends JpaRepository<Person, Integer> {
}
