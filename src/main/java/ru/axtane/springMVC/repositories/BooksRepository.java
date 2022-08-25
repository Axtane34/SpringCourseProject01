package ru.axtane.springMVC.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.axtane.springMVC.models.Book;

@Repository
public interface BooksRepository extends JpaRepository<Book, Integer> {
}
