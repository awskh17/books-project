package firsProject.booksProject.Services;

import firsProject.booksProject.Dtos.BookDto;
import org.springframework.boot.CommandLineRunner;

import java.util.List;

public interface BookService {
BookDto addBook(BookDto bookDto);
BookDto updateBookById(BookDto bookDto,long id);
void deleteBook(long id);
List<BookDto> getAllBooks();
List<BookDto> getAllBooksByTitle(String title);
List<BookDto> getAllBooksByPublisher(String publisher);
List<BookDto> getAllBooksByAuthors(List<String> authors);
List<BookDto> getBookBySummary(String summary);
BookDto getBookById(long id);
CommandLineRunner userup(String name,String pass);
}
