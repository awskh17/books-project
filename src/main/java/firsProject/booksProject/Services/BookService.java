package firsProject.booksProject.Services;

import firsProject.booksProject.Dtos.BookDto;
import firsProject.booksProject.Entity.MyUser;
import firsProject.booksProject.Entity.SearchPro;

import java.util.Date;
import java.util.List;
import java.util.Set;

public interface BookService {
BookDto addBook(BookDto bookDto) throws Exception;
BookDto updateBookById(BookDto bookDto,long id);
BookDto deleteBook(long id);
List<BookDto> getAllBooks();
List<BookDto> getAllBooksByTitle(String title);
List<BookDto> getAllBooksByPublisher(String publisher);
List<BookDto> getAllBooksByAuthors(List<String> authors);
List<BookDto> getBookBySummary(String summary);
BookDto getBookById(long id);
MyUser adduser(String name,String pass);
List<BookDto> searchpro(SearchPro sp, Set<String> authors);
}
