package firsProject.booksProject.Controllers;

import firsProject.booksProject.Dtos.BookDto;
import firsProject.booksProject.Services.BookService;
import firsProject.booksProject.Services.PublisherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Controller
@EnableWebMvc
@RequestMapping("/front/api/books")
public class FrontBookController {

    private BookService bookService;
    private PublisherService publisherService;

    public FrontBookController(BookService bookService, PublisherService publisherService) {
        this.bookService = bookService;
        this.publisherService = publisherService;
    }

    @GetMapping("/get")
    public String getBooks(Model model) {
        model.addAttribute("book", bookService.getAllBooks());
        return "/allBooks";
    }
    @GetMapping("/add")
    public String addBook(Model model) {
        BookDto bookDto = new BookDto();
        model.addAttribute("book", bookDto);
        return "addBook";
    }
    @GetMapping("update/{id}")
    public String updateForm(@PathVariable(value = "id") long id, Model model) {
        BookDto bookDto = bookService.getBookById(id);
        model.addAttribute("book", bookDto);
        bookService.deleteBook(bookDto.getId());
        bookService.addBook(bookDto);
        return "modifyBook";
    }
    @PostMapping("/save")
    public String saveBook(@ModelAttribute("book") BookDto bookDto) {
        if(publisherService.findPublisherById(bookDto.getId())==null) return "redirect:/front/api/publishers/add";
        bookService.addBook(bookDto);

        return "redirect:/front/api/books/get";
    }
    @GetMapping("/deleteBook/{id}")
    public String deleteThroughId(@PathVariable(value = "id") long id) {
    bookService.deleteBook(id);
    return "redirect:/front/api/books/get";
}
    @GetMapping("get/{id}")
    public String getBook(@PathVariable(value = "id") long id, Model model) {
        model.addAttribute("book", bookService.getBookById(id));
        return "getBook";
    }
}
