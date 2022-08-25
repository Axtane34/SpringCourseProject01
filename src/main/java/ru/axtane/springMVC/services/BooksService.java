package ru.axtane.springMVC.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.axtane.springMVC.models.Book;
import ru.axtane.springMVC.models.Person;
import ru.axtane.springMVC.repositories.BooksRepository;
import ru.axtane.springMVC.repositories.PeopleRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class BooksService {
    private final BooksRepository booksRepository;
    private final PeopleRepository peopleRepository;
    
    @Autowired
    public BooksService(BooksRepository booksRepository, PeopleRepository peopleRepository) {
        this.booksRepository = booksRepository;
        this.peopleRepository = peopleRepository;
    }

    public List<Book> findAll(){
        return booksRepository.findAll();
    }

    public Book findById(int id){
        return booksRepository.findById(id).orElse(null);
    }

    @Transactional
    public void save(Book person){
        booksRepository.save(person);
    }

    @Transactional
    public void update(int id, Book updatedBook){
        updatedBook.setBook_id(id);
        booksRepository.save(updatedBook);
    }

    @Transactional
    public void delete(int id){
        booksRepository.deleteById(id);
    }

    @Transactional
    public void updateOwner(int id, int person_id){
        Book book = booksRepository.findById(id).orElse(null);
        Person person = peopleRepository.findById(person_id).orElse(null);
        if (person != null && book != null) {
                person.addBook(book);
        }
        /*jdbcTemplate.update("UPDATE book SET person_id=? WHERE book_id=?", person_id, id);*/
    }
    @Transactional
    public void deleteOwner(int id){
        Book book = booksRepository.findById(id).orElse(null);
        if (book != null) {
            book.getOwner().getBooks().remove(book);
            book.setOwner(null);
        }
        /*jdbcTemplate.update("UPDATE book SET person_id=NULL WHERE book_id=?", id);*/
    }
}
