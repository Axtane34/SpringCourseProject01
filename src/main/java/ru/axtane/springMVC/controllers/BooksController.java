package ru.axtane.springMVC.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.axtane.springMVC.dao.BookDAO;
import ru.axtane.springMVC.dao.PersonDAO;
import ru.axtane.springMVC.models.Book;
import ru.axtane.springMVC.models.Person;

import javax.validation.Valid;

@Controller
@RequestMapping("/books")
public class BooksController {
    private final BookDAO bookDAO;
    private final PersonDAO personDAO;

    @Autowired
    public BooksController(BookDAO bookDAO, PersonDAO personDAO) {
        this.bookDAO = bookDAO;
        this.personDAO = personDAO;
    }

    @GetMapping
    public String index(Model model){
        model.addAttribute("books", bookDAO.index());
        return "books/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model book, Model people, Model owner, @ModelAttribute("person") Person person){
        book.addAttribute("book", bookDAO.show(id));
        people.addAttribute("people", personDAO.index());
        if (bookDAO.show(id).getPerson_id()!=null){
            owner.addAttribute("owner", personDAO.show(bookDAO.show(id).getPerson_id()));
        }
        return "books/show";
    }

    @PatchMapping("/{id}/addOwner")
    public String editOwner(@ModelAttribute("person") Person person, BindingResult bindingResult,
                            RedirectAttributes redirectAttributes, @PathVariable("id") int id){
        redirectAttributes.addAttribute("id", id);
        if (bindingResult.hasErrors())
            return "books/show";
        if(bookDAO.show(id).getPerson_id() == null || bookDAO.show(id).getPerson_id()!=person.getPerson_id()) {
            bookDAO.updateOwner(id, person.getPerson_id());
        }
        return "redirect:/books/{id}";
    }

    @PatchMapping("/{id}/deleteOwner")
    public String deleteOwner(@ModelAttribute("person") Person person, BindingResult bindingResult,
                            RedirectAttributes redirectAttributes, @PathVariable("id") int id){
        redirectAttributes.addAttribute("id", id);
        if (bindingResult.hasErrors())
            return "books/show";
        if(bookDAO.show(id).getPerson_id()!=null) {
            bookDAO.deleteOwner(id);
        }
        return "redirect:/books/{id}";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("book") Book book) {
        return "books/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "books/new";

        bookDAO.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model) {
        model.addAttribute("book", bookDAO.show(id));
        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String edit(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult,
                       @PathVariable("id") int id){
        if (bindingResult.hasErrors())
            return "books/edit";

        bookDAO.update(id, book);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        bookDAO.delete(id);
        return "redirect:/books";
    }
}
