package firsProject.booksProject.BooksProject;

import firsProject.booksProject.Dtos.BookDto;
import firsProject.booksProject.Entity.Author;
import firsProject.booksProject.Entity.Publisher;
import firsProject.booksProject.Mapper.BookMapper;
import firsProject.booksProject.Services.BookService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.Calendar;
import java.util.Date;

@WithMockUser(username="admin",roles={"ADMIN"})
public class BooksProjectTest {
    @Test
    public void shouldCreateNewBook(){
        BookDto bookDto=new BookDto();
        Author author=new Author();
        Publisher publisher=new Publisher();
        publisher.setName("aws1");
        author.setName("aws");
        bookDto.setTitle("x");
        bookDto.setType("edu");
        bookDto.setSummary("x");
        bookDto.setDateOfPublish(new Date(2004, Calendar.NOVEMBER,23));
        bookDto.setNumOfPublish(10);
        bookDto.getAuthors().add(author);
        bookDto.setPublisher(publisher);

        var test = BookMapper.mapToBook(bookDto);
        Assertions.assertEquals(bookDto.getType(),test.getType(),"Book type is not like You insert it");
        Assertions.assertEquals(bookDto.getSummary(),test.getSummary(),"Book summary is not like You insert it");
        Assertions.assertEquals(bookDto.getPublisher(),test.getPublisher(),"Book publisher is not like You insert it");
        Assertions.assertEquals(bookDto.getTitle(),test.getTitle(),"Book title is not like You insert it");
        Assertions.assertEquals(bookDto.getDateOfPublish(),test.getDateOfPublish(),"Book Date is not like You insert it");
        Assertions.assertEquals(bookDto.getNumOfPublish(),test.getNumOfPublish(),"Book numofpublish is not like You insert it");
        Assertions.assertEquals(bookDto.getAuthors(),test.getAuthors(),"Book Authors is not like You insert it");
    }
}
