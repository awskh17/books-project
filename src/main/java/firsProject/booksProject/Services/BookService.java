package firsProject.booksProject.Services;

import firsProject.booksProject.Dtos.BookDto;
import firsProject.booksProject.Entity.Author;
import firsProject.booksProject.Entity.Publisher;

import java.util.List;

public interface BookService {
BookDto addBook(BookDto bookDto);
BookDto updateBookById(BookDto bookDto,long id);
void deleteBook(long id);
List<BookDto> getAllBooks();
BookDto getBookByTitle(String title);
List<BookDto> getBookByPublisher(Publisher publisher);
List<BookDto> getBookByAuthors(List<Author> authors);
List<BookDto> getBookBySummary(String summary);
BookDto getBookById(long id);
}
