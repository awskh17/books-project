package firsProject.booksProject.Mapper;

import firsProject.booksProject.Dtos.BookDto;
import firsProject.booksProject.Entity.Book;

public class BookMapper {

    public static BookDto mapToBookDTO(Book book) {
        BookDto bookDto = new BookDto(
                book.getId(), book.getTitle(), book.getType(),
                book.getSummary(),book.getDateOfPublish(), book.getNumOfPublish(),
                book.getAuthors(),book.getPublisher()
        );
        return bookDto;
    }
   public static Book mapToBook(BookDto bookDto) {
        Book book = new Book(
                bookDto.getId(), bookDto.getTitle(), bookDto.getType(),
                bookDto.getSummary(),bookDto.getDateOfPublish(), bookDto.getNumOfPublish(),
                bookDto.getAuthors(),bookDto.getPublisher()
        );
        return book;
    }
}
