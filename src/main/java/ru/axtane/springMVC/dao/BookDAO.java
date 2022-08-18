package ru.axtane.springMVC.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.axtane.springMVC.models.Book;
import ru.axtane.springMVC.models.Person;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class BookDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> index(){
        return jdbcTemplate.query("SELECT * FROM book", new BeanPropertyRowMapper<>(Book.class));
    }

    public Book show(int id){
        return jdbcTemplate.query("SELECT * FROM book WHERE book_id=?",
                new Object[]{id}, new BeanPropertyRowMapper<>(Book.class)).stream().findAny().orElse(null);
    }

    public List<Book> showByPersonId(int id){
        return jdbcTemplate.query("SELECT * FROM book WHERE person_id=?",
                new Object[]{id}, new BeanPropertyRowMapper<>(Book.class));
    }

    public void save(Book book){
        jdbcTemplate.update("INSERT INTO book(name, author, yearOfWriting) VALUES(?, ?, ?)", book.getName(), book.getAuthor(), book.getYearOfWriting());
    }

    public void update(int id, Book book){
        jdbcTemplate.update("UPDATE book SET name=?, author=?, yearOfWriting=? WHERE book_id=?", book.getName(), book.getAuthor(), book.getYearOfWriting(), id);
    }

    public void updateOwner(int id, int person_id){
        jdbcTemplate.update("UPDATE book SET person_id=? WHERE book_id=?", person_id, id);
    }

    public void deleteOwner(int id){
        jdbcTemplate.update("UPDATE book SET person_id=NULL WHERE book_id=?", id);
    }

    public void delete(int id){
        jdbcTemplate.update("DELETE FROM book WHERE book_id=?", id);
    }
}

