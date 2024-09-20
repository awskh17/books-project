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
@RequestMapping("/")
public class FrontBookController {

    private BookService bookService;
    Set<String> authorstmp =new HashSet<>();
    Set<String> authorstmp2 =new HashSet<>();

    public FrontBookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/")
    public String index()
    {
        return "index";
    }
    @GetMapping("/front/api/books/get")
    public String getBooks(Model model) {
        model.addAttribute("book", bookService.getAllBooks());
        return "/allBooks";
    }
    @GetMapping("/front/api/books/add")
    public String addBook(Model model) {
        BookDto bookDto = new BookDto();
        String author=new String();
        model.addAttribute("book", bookDto);
        model.addAttribute("author", author);
        model.addAttribute("authorstmp", authorstmp);
        return "addBook";
    }
    @GetMapping("/front/api/books/update/{id}")
    public String updateForm(@PathVariable(value = "id") long id, Model model) {
        BookDto bookDto = bookService.getBookById(id);
        String author=new String();
        authorstmp.addAll(bookDto.getAuthors());
        model.addAttribute("authorstmp", authorstmp);
        model.addAttribute("author", author);
        model.addAttribute("book", bookDto);
        return "modifyBook";
    }
    @PostMapping("/front/api/books/modify/{id}")
    public String modify(@PathVariable long id,@ModelAttribute("book") BookDto bookDto)
    {
        bookDto.setAuthors(authorstmp);
        bookService.updateBookById(bookDto,id);
        authorstmp.clear();
        return "redirect:/front/api/books/get";
    }
    @PostMapping("/front/api/books/addAuthor")
    public String addAuthor(@RequestParam String author){
        if(!Objects.equals(author, ""))
        {authorstmp.add(author);}
        return "redirect:/front/api/books/add";
    }

    @PostMapping("/front/api/books/modify/addAuthor/{id}")
    public String modifyAddAuthor(@RequestParam String author,@PathVariable long id){
        if(!Objects.equals(author, ""))
        {authorstmp.add(author);}
        System.out.println(authorstmp);
        return "redirect:/front/api/books/update/"+id;
    }

    @PostMapping("/front/api/books/save")
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
    @GetMapping("/front/api/books/deleteBook/{id}")
    public String deleteThroughId(@PathVariable(value = "id") long id) {
    bookService.deleteBook(id);
    return "redirect:/front/api/books/get";
}
    @GetMapping("/front/api/books/delAuthor/search/{author}")
    public String delAuthor(@PathVariable(value = "author") String author)
    {
        if(!authorstmp2.isEmpty())
            authorstmp2.remove(author);
        return "redirect:/front/api/books/searchAuthor";
    }
    @GetMapping("/front/api/books/delAuthor/{author}/{id}")
    public String delAuthor(@PathVariable(value = "author") String author,@PathVariable long id)
    {
        if(authorstmp.size()>1)
        authorstmp.remove(author);
        return "redirect:/front/api/books/update/"+id;
    }
    @GetMapping("/front/api/books/searchId")
    public String getId(Model model)
    {
     Long id = null;
     model.addAttribute("id",id);
     return "searchId";
    }

    @PostMapping("/front/api/books/findById")
    public String findById(@ModelAttribute("id") long id){
        return "redirect:getBookById/"+id;
    }
    @GetMapping("/front/api/books/getBookById/{id}")
    public String getBookById(@PathVariable(value = "id") long id, Model model) {
        if(id<=0) throw new BookNotFoundException("Id is not true");
        model.addAttribute("book", bookService.getBookById(id));
        return "SearchedById";
    }

    @GetMapping("/front/api/books/searchPublisher")
    public String getPublisher(Model model)
    {
        String publisherName=null;
        model.addAttribute("publisherName",publisherName);
        return "searchPublisher";
    }
    @PostMapping("/front/api/books/findByPublisher")
    public String findByPublisher(@ModelAttribute("publisherName") String publisherName ){
    return "redirect:getBookByPublisher/"+publisherName;
    }

    @GetMapping("/front/api/books/getBookByPublisher/{publisherName}")
    public String getBookByPublisher(@PathVariable(value = "publisherName") String publishername, Model model) {
        model.addAttribute("book", bookService.getAllBooksByPublisher(publishername));
        return "SearchedByPublisher";
    }
    @PostMapping("/front/api/books/getBookByAuthors/Final")
    public String getBookByPublisher(Model model) {
        model.addAttribute("book", bookService.getAllBooksByAuthors(authorstmp2.stream().toList()));
        authorstmp2.clear();
        return "SearchedByPublisher";
    }
    @PostMapping("/front/api/books/addAuthorForSearch")
    public String addAuthorSearch(@RequestParam String author){
        if(!Objects.equals(author, ""))
        {authorstmp2.add(author);}
        return "redirect:/front/api/books/searchAuthor";
    }
    @GetMapping("/front/api/books/searchAuthor")
    public String searchAuthor(Model model) {
        String author = null;
        model.addAttribute("author", author);
        model.addAttribute("authorstmp",authorstmp2);
        return "searchAuthor";
    }

    @GetMapping("/front/api/books/searchTitle")
    public String getTitle(Model model)
    {
       String title = null;
        model.addAttribute("title",title);
        return "searchTitle";
    }

    @PostMapping("/front/api/books/findByTitle")
    public String findById(@ModelAttribute("title")  String title) {
        return "redirect:getAllBooksByTitle/"+title;
    }
    @GetMapping("/front/api/books/getAllBooksByTitle/{title}")
    public String getBookByTitle(@PathVariable(value = "title") String title, Model model) {
        List<BookDto> books =bookService.getAllBooksByTitle(title);
        model.addAttribute("book", books);
        return "SearchedByTitle";
    }

    @GetMapping("/front/api/books/searchSummary")
    public String getSummary(Model model)
    {
        String Summary = null;
        model.addAttribute("summary",Summary);
        return "searchSummary";
    }

    @PostMapping("/front/api/books/findBySummary")
    public String findBySummary(@ModelAttribute("summary")  String summary) {
        return "redirect:getBookBySummary/"+summary;
    }
    @GetMapping("/front/api/books/getBookBySummary/{summary}")
    public String getBookBySummary(@PathVariable(value = "summary") String summary, Model model) {
        model.addAttribute("book", bookService.getBookBySummary(summary));
        return "SearchedBySummary";
    }

}
