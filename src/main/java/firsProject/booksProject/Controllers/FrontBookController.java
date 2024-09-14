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
@RequestMapping("/front/api/books")
public class FrontBookController {

    private BookService bookService;
    Set<String> authorstmp =new HashSet<>();
    Set<String> authorstmp2 =new HashSet<>();

    public FrontBookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/get")
    public String getBooks(Model model) {
        model.addAttribute("book", bookService.getAllBooks());
        return "/allBooks";
    }
    @GetMapping("/add")
    public String addBook(Model model) {
        BookDto bookDto = new BookDto();
        String author=new String();
        model.addAttribute("book", bookDto);
        model.addAttribute("author", author);
        return "addBook";
    }
    @GetMapping("update/{id}")
    public String updateForm(@PathVariable(value = "id") long id, Model model) {
        BookDto bookDto = bookService.getBookById(id);
        String author=new String();
        model.addAttribute("author", author);
        model.addAttribute("book", bookDto);
        bookService.deleteBook(bookDto.getId());
        return "modifyBook";
    }
    @PostMapping("/addAuthor")
    public String addAuthor(@RequestParam String author){
        if(!Objects.equals(author, ""))
        {authorstmp.add(author);}
        return "redirect:/front/api/books/add";
    }
    @PostMapping("/save")
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
    @GetMapping("/deleteBook/{id}")
    public String deleteThroughId(@PathVariable(value = "id") long id) {
    bookService.deleteBook(id);
    return "redirect:/front/api/books/get";
}
    @GetMapping("/searchId")
    public String getId(Model model)
    {
     Long id = null;
     model.addAttribute("id",id);
     return "searchId";
    }

    @PostMapping("/findById")
    public String findById(@ModelAttribute("id") long id){
        return "redirect:getBookById/"+id;
    }
    @GetMapping("/getBookById/{id}")
    public String getBookById(@PathVariable(value = "id") long id, Model model) {
        if(id<=0) throw new BookNotFoundException("Id is not true");
        model.addAttribute("book", bookService.getBookById(id));
        return "SearchedById";
    }

    @GetMapping("/searchPublisher")
    public String getPublisher(Model model)
    {
        String publisherName=null;
        model.addAttribute("publisherName",publisherName);
        return "searchPublisher";
    }
    @PostMapping("/findByPublisher")
    public String findByPublisher(@ModelAttribute("publisherName") String publisherName ){
    return "redirect:getBookByPublisher/"+publisherName;
    }

    @GetMapping("/getBookByPublisher/{publisherName}")
    public String getBookByPublisher(@PathVariable(value = "publisherName") String publishername, Model model) {
        model.addAttribute("book", bookService.getAllBooksByPublisher(publishername));
        return "SearchedByPublisher";
    }
    @PostMapping("/getBookByAuthors/Final")
    public String getBookByPublisher(Model model) {
        model.addAttribute("book", bookService.getAllBooksByAuthors(authorstmp2.stream().toList()));
        authorstmp2.clear();
        return "SearchedByPublisher";
    }
    @PostMapping("/addAuthorForSearch")
    public String addAuthorSearch(@RequestParam String author){
        if(!Objects.equals(author, ""))
        {authorstmp2.add(author);}
        return "redirect:/front/api/books/searchAuthor";
    }
    @GetMapping("/searchAuthor")
    public String searchAuthor(Model model) {
        String author = null;
        model.addAttribute("author", author);
        return "searchAuthor";
    }

    @GetMapping("/searchTitle")
    public String getTitle(Model model)
    {
       String title = null;
        model.addAttribute("title",title);
        return "searchTitle";
    }

    @PostMapping("/findByTitle")
    public String findById(@ModelAttribute("title")  String title) {
        return "redirect:getAllBooksByTitle/"+title;
    }
    @GetMapping("/getAllBooksByTitle/{title}")
    public String getBookByTitle(@PathVariable(value = "title") String title, Model model) {
        List<BookDto> books =bookService.getAllBooksByTitle(title);
        model.addAttribute("book", books);
        return "SearchedByTitle";
    }

    @GetMapping("/searchSummary")
    public String getSummary(Model model)
    {
        String Summary = null;
        model.addAttribute("summary",Summary);
        return "searchSummary";
    }

    @PostMapping("/findBySummary")
    public String findBySummary(@ModelAttribute("summary")  String summary) {
        return "redirect:getBookBySummary/"+summary;
    }
    @GetMapping("/getBookBySummary/{summary}")
    public String getBookBySummary(@PathVariable(value = "summary") String summary, Model model) {
        model.addAttribute("book", bookService.getBookBySummary(summary));
        return "SearchedBySummary";
    }

}
