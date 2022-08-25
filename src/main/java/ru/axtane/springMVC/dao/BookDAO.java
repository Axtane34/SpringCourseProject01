package ru.axtane.springMVC.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.persistence.EntityManagerFactory;

@Component
public class BookDAO {
    private final EntityManagerFactory entityManagerFactory;

    @Autowired
    public BookDAO(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }
    
    /*@Transactional(readOnly = true)
    public List<Book> index(){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        return entityManager.createQuery("select b from Book b", Book.class).getResultList();
        *//*return jdbcTemplate.query("SELECT * FROM book", new BeanPropertyRowMapper<>(Book.class));*//*
    }

    @Transactional(readOnly = true)
    public Book show(int id){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        return entityManager.find(Book.class, id);
    }

    @Transactional
    public void save(Book book){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.persist(book);
        *//*jdbcTemplate.update("INSERT INTO book(name, author, yearOfWriting) VALUES(?, ?, ?)", book.getName(), book.getAuthor(), book.getYearOfWriting());*//*
    }
    @Transactional
    public void update(int id, Book updatedBook){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Book book = entityManager.find(Book.class, id);
        Book.updateBook(book, updatedBook);

        *//*jdbcTemplate.update("UPDATE book SET name=?, author=?, yearOfWriting=? WHERE book_id=?", book.getName(), book.getAuthor(), book.getYearOfWriting(), id);*//*
    }*/
    /*@Transactional
    public void updateOwner(int id, int person_id){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Book book = entityManager.find(Book.class, id);
        Person person = entityManager.find(Person.class, person_id);
        person.addBook(book);
        entityManager.persist(person);
        entityManager.persist(book);
        *//*jdbcTemplate.update("UPDATE book SET person_id=? WHERE book_id=?", person_id, id);*//*
    }
    @Transactional
    public void deleteOwner(int id){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Book book = entityManager.find(Book.class, id);
        book.getOwner().getBooks().remove(book);
        book.setOwner(null);
        entityManager.persist(book.getOwner());
        entityManager.persist(book);
        *//*jdbcTemplate.update("UPDATE book SET person_id=NULL WHERE book_id=?", id);*//*
    }*/
    /*@Transactional
    public void delete(int id){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.remove(entityManager.find(Book.class, id));
        *//*jdbcTemplate.update("DELETE FROM book WHERE book_id=?", id);*//*
    }*/
}

