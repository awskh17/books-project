package firsProject.booksProject.Services;

import firsProject.booksProject.Dtos.BookDto;
import firsProject.booksProject.Entity.MyUser;

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
MyUser adduser(String name,String pass);
//void test() throws InterruptedException;
}
