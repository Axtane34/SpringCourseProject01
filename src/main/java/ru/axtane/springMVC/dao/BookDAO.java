package ru.axtane.springMVC.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.axtane.springMVC.models.Book;
import ru.axtane.springMVC.models.Person;

import java.util.List;

@Component
public class BookDAO {
    private final SessionFactory sessionFactory;

    @Autowired
    public BookDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional(readOnly = true)
    public List<Book> index(){
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select b from Book b", Book.class).getResultList();
        /*return jdbcTemplate.query("SELECT * FROM book", new BeanPropertyRowMapper<>(Book.class));*/
    }

    @Transactional(readOnly = true)
    public Book show(int id){
        Session session = sessionFactory.getCurrentSession();
        return session.get(Book.class, id);
    }

    @Transactional
    public void save(Book book){
        Session session = sessionFactory.getCurrentSession();
        session.persist(book);
        /*jdbcTemplate.update("INSERT INTO book(name, author, yearOfWriting) VALUES(?, ?, ?)", book.getName(), book.getAuthor(), book.getYearOfWriting());*/
    }
    @Transactional
    public void update(int id, Book updatedBook){
        Session session = sessionFactory.getCurrentSession();
        Book book = session.get(Book.class, id);
        Book.updateBook(book, updatedBook);

        /*jdbcTemplate.update("UPDATE book SET name=?, author=?, yearOfWriting=? WHERE book_id=?", book.getName(), book.getAuthor(), book.getYearOfWriting(), id);*/
    }
    @Transactional
    public void updateOwner(int id, int person_id){
        Session session = sessionFactory.getCurrentSession();
        Book book = session.get(Book.class, id);
        Person person = session.get(Person.class, person_id);
        person.addBook(book);
        /*jdbcTemplate.update("UPDATE book SET person_id=? WHERE book_id=?", person_id, id);*/
    }
    @Transactional
    public void deleteOwner(int id){
        Session session = sessionFactory.getCurrentSession();
        Book book = session.get(Book.class, id);
        book.getOwner().getBooks().remove(book);
        book.setOwner(null);
        /*jdbcTemplate.update("UPDATE book SET person_id=NULL WHERE book_id=?", id);*/
    }
    @Transactional
    public void delete(int id){
        Session session = sessionFactory.getCurrentSession();
        session.remove(session.get(Book.class, id));
        /*jdbcTemplate.update("DELETE FROM book WHERE book_id=?", id);*/
    }
}

