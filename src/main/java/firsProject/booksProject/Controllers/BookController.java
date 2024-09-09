package firsProject.booksProject.Controllers;

import firsProject.booksProject.Dtos.BookDto;
import firsProject.booksProject.Entity.Author;
import firsProject.booksProject.Entity.Publisher;
import firsProject.booksProject.Exceptions.BookNotFoundException;
import firsProject.booksProject.Services.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {
    BookService bookService;


    public BookController(BookService bookService) {
        this.bookService = bookService;
    }
    @DeleteMapping("{id}")
    ResponseEntity<String> deleteBook(@PathVariable("id") long id) {
        bookService.deleteBook(id);
        return new ResponseEntity<>("Review deleted successfully", HttpStatus.OK);
    }

    @PostMapping
    ResponseEntity<String> addBook(@RequestBody BookDto bookDto) {
        BookDto savedBook=bookService.addBook(bookDto);
        return new ResponseEntity<>(savedBook.toString(), HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    ResponseEntity<String> updateBook(@PathVariable("id") long id, @RequestBody BookDto bookDto) {
        BookDto updatedBook=bookService.updateBookById(bookDto,id);
        return new ResponseEntity<>(updatedBook.toString(), HttpStatus.OK);
    }

    @GetMapping("/get")
    ResponseEntity<String> getAllBooks() {
        List<BookDto> booksDto=bookService.getAllBooks();
        return new ResponseEntity<>(booksDto.toString(), HttpStatus.OK);
    }

   @GetMapping("/get/ByTitle/{title}")
    ResponseEntity<String> getByTitle(@PathVariable("title") String title) {
        BookDto bookDto=bookService.getBookByTitle(title);
       if(bookDto==null) throw new BookNotFoundException("Book with "+title+" not found");
        return new ResponseEntity<>(bookDto.toString(), HttpStatus.OK);
   }

   @GetMapping("/get/ByAuthors")
    ResponseEntity<String> getByAuthor(@RequestBody List<Author> authors) {
        List<BookDto> booksDto=bookService.getBookByAuthors(authors);
        return new ResponseEntity<>(booksDto.toString(), HttpStatus.OK);
   }

   @GetMapping("/get/ByPublisher")
    ResponseEntity<String> getByPublisher(@RequestBody Publisher publisher) {
        List<BookDto> booksDto=bookService.getBookByPublisher(publisher);
        return new ResponseEntity<>(booksDto.toString(), HttpStatus.OK);
   }

   @GetMapping("/get/BySummary")
    ResponseEntity<String> getBySummary(@RequestBody String summary) {
        List<BookDto> booksDto=bookService.getBookBySummary(summary);
        if(booksDto.size()==0) throw new BookNotFoundException("Book with "+summary+" not found");
        return new ResponseEntity<>(booksDto.toString(), HttpStatus.OK);
   }

}
