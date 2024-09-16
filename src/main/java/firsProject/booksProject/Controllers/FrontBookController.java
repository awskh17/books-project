package firsProject.booksProject.Controllers;

import firsProject.booksProject.Dtos.BookDto;
import firsProject.booksProject.Exceptions.AuthorNotAddedException;
import firsProject.booksProject.Exceptions.BookNotFoundException;
import firsProject.booksProject.Exceptions.DateNotTrueException;
import firsProject.booksProject.Exceptions.NumOfPublishException;
import firsProject.booksProject.Services.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.*;

@Controller
@EnableWebMvc
<<<<<<< HEAD
@RequestMapping("/front/api/books")
=======
@RequestMapping("/")
>>>>>>> main
public class FrontBookController {

    private BookService bookService;
    Set<String> authorstmp =new HashSet<>();
    Set<String> authorstmp2 =new HashSet<>();

    public FrontBookController(BookService bookService) {
        this.bookService = bookService;
    }

<<<<<<< HEAD
    @GetMapping("/get")
=======
    @GetMapping("/")
    public String index()
    {
        return "index";
    }
    @GetMapping("/front/api/books/get")
>>>>>>> main
    public String getBooks(Model model) {
        model.addAttribute("book", bookService.getAllBooks());
        return "/allBooks";
    }
<<<<<<< HEAD
    @GetMapping("/add")
=======
    @GetMapping("/front/api/books/add")
>>>>>>> main
    public String addBook(Model model) {
        BookDto bookDto = new BookDto();
        String author=new String();
        model.addAttribute("book", bookDto);
        model.addAttribute("author", author);
        model.addAttribute("authorstmp", authorstmp);
        return "addBook";
    }
<<<<<<< HEAD
    @GetMapping("update/{id}")
=======
    @GetMapping("/front/api/books/update/{id}")
>>>>>>> main
    public String updateForm(@PathVariable(value = "id") long id, Model model) {
        BookDto bookDto = bookService.getBookById(id);
        String author=new String();
        model.addAttribute("authorstmp", authorstmp2);
        model.addAttribute("author", author);
        model.addAttribute("book", bookDto);
        bookService.deleteBook(bookDto.getId());
        return "modifyBook";
    }
<<<<<<< HEAD
    @PostMapping("/addAuthor")
=======
    @PostMapping("/front/api/books/addAuthor")
>>>>>>> main
    public String addAuthor(@RequestParam String author){
        if(!Objects.equals(author, ""))
        {authorstmp.add(author);}
        return "redirect:/front/api/books/add";
    }
<<<<<<< HEAD
    @PostMapping("/save")
=======
    @PostMapping("/front/api/books/save")
>>>>>>> main
    public String saveBook(@ModelAttribute("book") BookDto bookDto) {
        if(authorstmp.isEmpty()) {throw new AuthorNotAddedException("error there isnt any author here");}
        bookDto.setAuthors(authorstmp);
        if(bookDto.getDateOfPublish().compareTo(Date.from(LocalDate.now().atStartOfDay().toInstant(ZoneOffset.UTC))) == 1)
        {throw new DateNotTrueException("date is not Acceptable");}
        if(bookDto.getNumOfPublish()==0) throw new NumOfPublishException("numOfPublish is not acceptable");
        bookService.addBook(bookDto);
        authorstmp.clear();
        return "redirect:/front/api/books/get";
    }
<<<<<<< HEAD
    @GetMapping("/deleteBook/{id}")
=======
    @GetMapping("/front/api/books/deleteBook/{id}")
>>>>>>> main
    public String deleteThroughId(@PathVariable(value = "id") long id) {
    bookService.deleteBook(id);
    return "redirect:/front/api/books/get";
}
<<<<<<< HEAD
    @GetMapping("/searchId")
=======
    @GetMapping("/front/api/books/searchId")
>>>>>>> main
    public String getId(Model model)
    {
     Long id = null;
     model.addAttribute("id",id);
     return "searchId";
    }

<<<<<<< HEAD
    @PostMapping("/findById")
    public String findById(@ModelAttribute("id") long id){
        return "redirect:getBookById/"+id;
    }
    @GetMapping("/getBookById/{id}")
=======
    @PostMapping("/front/api/books/findById")
    public String findById(@ModelAttribute("id") long id){
        return "redirect:getBookById/"+id;
    }
    @GetMapping("/front/api/books/getBookById/{id}")
>>>>>>> main
    public String getBookById(@PathVariable(value = "id") long id, Model model) {
        if(id<=0) throw new BookNotFoundException("Id is not true");
        model.addAttribute("book", bookService.getBookById(id));
        return "SearchedById";
    }

<<<<<<< HEAD
    @GetMapping("/searchPublisher")
=======
    @GetMapping("/front/api/books/searchPublisher")
>>>>>>> main
    public String getPublisher(Model model)
    {
        String publisherName=null;
        model.addAttribute("publisherName",publisherName);
        return "searchPublisher";
    }
<<<<<<< HEAD
    @PostMapping("/findByPublisher")
=======
    @PostMapping("/front/api/books/findByPublisher")
>>>>>>> main
    public String findByPublisher(@ModelAttribute("publisherName") String publisherName ){
    return "redirect:getBookByPublisher/"+publisherName;
    }

<<<<<<< HEAD
    @GetMapping("/getBookByPublisher/{publisherName}")
=======
    @GetMapping("/front/api/books/getBookByPublisher/{publisherName}")
>>>>>>> main
    public String getBookByPublisher(@PathVariable(value = "publisherName") String publishername, Model model) {
        model.addAttribute("book", bookService.getAllBooksByPublisher(publishername));
        return "SearchedByPublisher";
    }
<<<<<<< HEAD
    @PostMapping("/getBookByAuthors/Final")
=======
    @PostMapping("/front/api/books/getBookByAuthors/Final")
>>>>>>> main
    public String getBookByPublisher(Model model) {
        model.addAttribute("book", bookService.getAllBooksByAuthors(authorstmp2.stream().toList()));
        authorstmp2.clear();
        return "SearchedByPublisher";
    }
<<<<<<< HEAD
    @PostMapping("/addAuthorForSearch")
=======
    @PostMapping("/front/api/books/addAuthorForSearch")
>>>>>>> main
    public String addAuthorSearch(@RequestParam String author){
        if(!Objects.equals(author, ""))
        {authorstmp2.add(author);}
        return "redirect:/front/api/books/searchAuthor";
    }
<<<<<<< HEAD
    @GetMapping("/searchAuthor")
=======
    @GetMapping("/front/api/books/searchAuthor")
>>>>>>> main
    public String searchAuthor(Model model) {
        String author = null;
        model.addAttribute("author", author);
        return "searchAuthor";
    }

<<<<<<< HEAD
    @GetMapping("/searchTitle")
=======
    @GetMapping("/front/api/books/searchTitle")
>>>>>>> main
    public String getTitle(Model model)
    {
       String title = null;
        model.addAttribute("title",title);
        return "searchTitle";
    }

<<<<<<< HEAD
    @PostMapping("/findByTitle")
    public String findById(@ModelAttribute("title")  String title) {
        return "redirect:getAllBooksByTitle/"+title;
    }
    @GetMapping("/getAllBooksByTitle/{title}")
=======
    @PostMapping("/front/api/books/findByTitle")
    public String findById(@ModelAttribute("title")  String title) {
        return "redirect:getAllBooksByTitle/"+title;
    }
    @GetMapping("/front/api/books/getAllBooksByTitle/{title}")
>>>>>>> main
    public String getBookByTitle(@PathVariable(value = "title") String title, Model model) {
        List<BookDto> books =bookService.getAllBooksByTitle(title);
        model.addAttribute("book", books);
        return "SearchedByTitle";
    }

<<<<<<< HEAD
    @GetMapping("/searchSummary")
=======
    @GetMapping("/front/api/books/searchSummary")
>>>>>>> main
    public String getSummary(Model model)
    {
        String Summary = null;
        model.addAttribute("summary",Summary);
        return "searchSummary";
    }

<<<<<<< HEAD
    @PostMapping("/findBySummary")
    public String findBySummary(@ModelAttribute("summary")  String summary) {
        return "redirect:getBookBySummary/"+summary;
    }
    @GetMapping("/getBookBySummary/{summary}")
=======
    @PostMapping("/front/api/books/findBySummary")
    public String findBySummary(@ModelAttribute("summary")  String summary) {
        return "redirect:getBookBySummary/"+summary;
    }
    @GetMapping("/front/api/books/getBookBySummary/{summary}")
>>>>>>> main
    public String getBookBySummary(@PathVariable(value = "summary") String summary, Model model) {
        model.addAttribute("book", bookService.getBookBySummary(summary));
        return "SearchedBySummary";
    }

}
