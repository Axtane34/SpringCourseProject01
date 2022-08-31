package ru.axtane.springMVC.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.axtane.springMVC.models.Book;
import ru.axtane.springMVC.models.Person;
import ru.axtane.springMVC.services.BooksService;
import ru.axtane.springMVC.services.PeopleService;

import javax.validation.Valid;

@Controller
@RequestMapping("/books")
public class BooksController {
    private final PeopleService peopleService;
    private final BooksService booksService;

    @Autowired
    public BooksController(PeopleService peopleService, BooksService booksService) {
        this.peopleService = peopleService;
        this.booksService = booksService;
    }

    @GetMapping
    public String index(Model model, @RequestParam(value = "page", required = false) Integer page,
                        @RequestParam(value = "books_per_page", required = false) Integer bpp,
                        @RequestParam(value = "sort_by_year", required = false) Boolean sort){
        if (sort==null) {
            if (page==null || bpp == null) {
                model.addAttribute("books", booksService.findAll());
            }else model.addAttribute("books", booksService.findAll(page, bpp));
        }else if (page == null || bpp == null){
            model.addAttribute("books", booksService.findAll(sort));
        }else model.addAttribute("books", booksService.findAll(page, bpp, sort));
        return "books/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model book, Model people, @ModelAttribute("person") Person person){
        book.addAttribute("book", booksService.findById(id));
        people.addAttribute("people", peopleService.findAll());
        /*if (bookDAO.show(id).getOwner()!=null){
            owner.addAttribute("owner", personDAO.show(bookDAO.show(id).getOwner().getPerson_id()));
        }*/
        return "books/show";
    }
    //Для будущих решений
   /* @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model, @ModelAttribute("person") Person person) {
        model.addAttribute("book", bookDAO.show(id));

        Optional<Person> bookOwner = personDAO.show(bookDAO.show(id).getPerson_id());

        if (bookOwner.isPresent())
            model.addAttribute("owner", bookOwner.get());
        else
            model.addAttribute("people", personDAO.index());

        return "books/show";
    }*/

    @PatchMapping("/{id}/addOwner")
    public String editOwner(@ModelAttribute("person") Person person,
                            RedirectAttributes redirectAttributes, @PathVariable("id") int id){
        redirectAttributes.addAttribute("id", id);
            booksService.updateOwner(id, person.getPerson_id());
        return "redirect:/books/{id}";
    }

    @PatchMapping("/{id}/deleteOwner")
    public String deleteOwner(RedirectAttributes redirectAttributes, @PathVariable("id") int id){
        redirectAttributes.addAttribute("id", id);
            booksService.deleteOwner(id);
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

        booksService.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model) {
        model.addAttribute("book", booksService.findById(id));
        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String edit(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult,
                       @PathVariable("id") int id){
        if (bindingResult.hasErrors())
            return "books/edit";

        booksService.update(id, book);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        booksService.delete(id);
        return "redirect:/books";
    }
}
