package firsProject.booksProject.BooksProject;

import firsProject.booksProject.Dtos.BookDto;
import firsProject.booksProject.Entity.Author;
import firsProject.booksProject.Entity.Book;
import firsProject.booksProject.Entity.Publisher;
import firsProject.booksProject.Mapper.BookMapper;
import firsProject.booksProject.Repositories.AuthorRepo;
import firsProject.booksProject.Repositories.BookRepo;
import firsProject.booksProject.Repositories.PublisherRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@WithMockUser(username="admin",roles={"ADMIN"})
@DataJpaTest(excludeAutoConfiguration= {BookRepo.class, PublisherRepo.class, AuthorRepo.class})
public class ReposTest {

    @Autowired
    private BookRepo bookRepo;
    @Autowired
    private PublisherRepo publisherRepo;
    @Autowired
    private AuthorRepo authorRepo;

    @Test
    public void addnewbook() throws Exception{
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
        var test = bookRepo.save(BookMapper.mapToBook(bookDto));
        Assertions.assertEquals(bookDto.getType(),test.getType(),"Book type is not like You insert it");
        Assertions.assertEquals(bookDto.getSummary(),test.getSummary(),"Book summary is not like You insert it");
        Assertions.assertEquals(bookDto.getPublisher(),test.getPublisher(),"Book publisher is not like You insert it");
        Assertions.assertEquals(bookDto.getTitle(),test.getTitle(),"Book title is not like You insert it");
        Assertions.assertEquals(bookDto.getDateOfPublish(),test.getDateOfPublish(),"Book Date is not like You insert it");
        Assertions.assertEquals(bookDto.getNumOfPublish(),test.getNumOfPublish(),"Book numofpublish is not like You insert it");
        Assertions.assertEquals(bookDto.getAuthors(),test.getAuthors(),"Book Authors is not like You insert it");
    }
    @Test
    public void deletebook() throws Exception{
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
        bookRepo.save(BookMapper.mapToBook(bookDto));
        bookRepo.delete(BookMapper.mapToBook(bookDto));
        Assertions.assertEquals(bookRepo.findBookById(bookDto.getId()),null,"something wrong with delete method");
    }
    @Test
    public void findAllBySummary(){
        BookDto bookDto=new BookDto();
        Author author=new Author();
        Publisher publisher=new Publisher();
        publisher.setName("aws1");
        author.setName("aws");
        bookDto.setTitle("x");
        bookDto.setType("edu");
        bookDto.setSummary("xxx");
        bookDto.setDateOfPublish(new Date(2004, Calendar.NOVEMBER,23));
        bookDto.setNumOfPublish(10);
        bookDto.getAuthors().add(author);
        bookDto.setPublisher(publisher);

        BookDto bookDto1=new BookDto();
        Author author1=new Author();
        Publisher publisher1=new Publisher();
        publisher1.setName("aws2");
        author1.setName("aws");
        bookDto1.setTitle("xx");
        bookDto1.setType("edu");
        bookDto1.setSummary("x");
        bookDto1.setDateOfPublish(new Date(2004, Calendar.NOVEMBER,23));
        bookDto1.setNumOfPublish(10);
        bookDto1.getAuthors().add(author);
        bookDto1.setPublisher(publisher);

        BookDto bookDto2=new BookDto();
        Author author2=new Author();
        Publisher publisher2=new Publisher();
        publisher2.setName("aws3");
        author2.setName("aws");
        bookDto2.setTitle("xxx");
        bookDto2.setType("edu");
        bookDto2.setSummary("x");
        bookDto2.setDateOfPublish(new Date(2004, Calendar.NOVEMBER,23));
        bookDto2.setNumOfPublish(10);
        bookDto2.getAuthors().add(author);
        bookDto2.setPublisher(publisher);

        bookRepo.save(BookMapper.mapToBook(bookDto));
        bookRepo.save(BookMapper.mapToBook(bookDto1));
        bookRepo.save(BookMapper.mapToBook(bookDto2));

        List<Book> books=bookRepo.findAllBySummary("x");
        Assertions.assertEquals(2,books.size(),"there is wrong in search by summary function");
    }

    @Test
    public void findAllByTitle(){
        BookDto bookDto=new BookDto();
        Author author=new Author();
        Publisher publisher=new Publisher();
        publisher.setName("aws1");
        author.setName("aws");
        bookDto.setTitle("x");
        bookDto.setType("edu");
        bookDto.setSummary("xxx");
        bookDto.setDateOfPublish(new Date(2004, Calendar.NOVEMBER,23));
        bookDto.setNumOfPublish(10);
        bookDto.getAuthors().add(author);
        bookDto.setPublisher(publisher);

        BookDto bookDto1=new BookDto();
        Author author1=new Author();
        Publisher publisher1=new Publisher();
        publisher1.setName("aws2");
        author1.setName("aws");
        bookDto1.setTitle("xx");
        bookDto1.setType("edu");
        bookDto1.setSummary("x");
        bookDto1.setDateOfPublish(new Date(2004, Calendar.NOVEMBER,23));
        bookDto1.setNumOfPublish(10);
        bookDto1.getAuthors().add(author);
        bookDto1.setPublisher(publisher);

        BookDto bookDto2=new BookDto();
        Author author2=new Author();
        Publisher publisher2=new Publisher();
        publisher2.setName("aws3");
        author2.setName("aws");
        bookDto2.setTitle("xxx");
        bookDto2.setType("edu");
        bookDto2.setSummary("x");
        bookDto2.setDateOfPublish(new Date(2004, Calendar.NOVEMBER,23));
        bookDto2.setNumOfPublish(10);
        bookDto2.getAuthors().add(author);
        bookDto2.setPublisher(publisher);

        bookRepo.save(BookMapper.mapToBook(bookDto));
        bookRepo.save(BookMapper.mapToBook(bookDto1));
        bookRepo.save(BookMapper.mapToBook(bookDto2));

        List<Book> books=bookRepo.findAllByTitle("x");
        Assertions.assertEquals(1,books.size(),"there is wrong in search by title function");
    }
    @Test
    public void findByPublisher(){
        BookDto bookDto=new BookDto();
        Author author=new Author();
        Publisher publisher=new Publisher();
        publisher.setName("aws");
        author.setName("aws");
        bookDto.setTitle("x");
        bookDto.setType("edu");
        bookDto.setSummary("xxx");
        bookDto.setDateOfPublish(new Date(2004, Calendar.NOVEMBER,23));
        bookDto.setNumOfPublish(10);
        bookDto.getAuthors().add(author);
        bookDto.setPublisher(publisher);

        BookDto bookDto1=new BookDto();
        Author author1=new Author();
        Publisher publisher1=new Publisher();
        publisher1.setName("aws1");
        author1.setName("aws");
        bookDto1.setTitle("xx");
        bookDto1.setType("edu");
        bookDto1.setSummary("x");
        bookDto1.setDateOfPublish(new Date(2004, Calendar.NOVEMBER,23));
        bookDto1.setNumOfPublish(10);
        bookDto1.getAuthors().add(author1);
        bookDto1.setPublisher(publisher1);

        BookDto bookDto2=new BookDto();
        Author author2=new Author();
        Publisher publisher2=new Publisher();
        publisher2.setName("aws2");
        author2.setName("aws");
        bookDto2.setTitle("xxx");
        bookDto2.setType("edu");
        bookDto2.setSummary("x");
        bookDto2.setDateOfPublish(new Date(2004, Calendar.NOVEMBER,23));
        bookDto2.setNumOfPublish(10);
        bookDto2.getAuthors().add(author2);
        bookDto2.setPublisher(publisher2);

        publisherRepo.save(publisher);
        publisherRepo.save(publisher1);
        publisherRepo.save(publisher2);

        publisher.getBooks().add(bookRepo.save(BookMapper.mapToBook(bookDto)));
        publisher1.getBooks().add(bookRepo.save(BookMapper.mapToBook(bookDto1)));
        publisher2.getBooks().add(bookRepo.save(BookMapper.mapToBook(bookDto2)));

        List<Book> books1=bookRepo.findAllByPublisher(publisher1.getId());
        List<Book> books2=bookRepo.findAllByPublisher(publisher2.getId());
        Assertions.assertEquals(1,books1.size(),"there is wrong in search by publisher function");
        Assertions.assertEquals("aws2",books2.getFirst().getPublisher().getName(),"there is wrong in search by publisher function");
    }
    @Test
    public void findByAuthor(){
        BookDto bookDto=new BookDto();
        Author author=new Author();
        Publisher publisher=new Publisher();
        publisher.setName("aws");
        author.setName("aws");
        bookDto.setTitle("x");
        bookDto.setType("edu");
        bookDto.setSummary("xxx");
        bookDto.setDateOfPublish(new Date(2004, Calendar.NOVEMBER,23));
        bookDto.setNumOfPublish(10);
        bookDto.getAuthors().add(author);
        bookDto.setPublisher(publisher);

        BookDto bookDto1=new BookDto();
        Author author1=new Author();
        Publisher publisher1=new Publisher();
        publisher1.setName("aws1");
        author1.setName("aws1");
        bookDto1.setTitle("xx");
        bookDto1.setType("edu");
        bookDto1.setSummary("x");
        bookDto1.setDateOfPublish(new Date(2004, Calendar.NOVEMBER,23));
        bookDto1.setNumOfPublish(10);
        bookDto1.getAuthors().add(author1);
        bookDto1.setPublisher(publisher1);

        BookDto bookDto2=new BookDto();
        Publisher publisher2=new Publisher();
        publisher2.setName("aws3");
        bookDto2.setTitle("xxx");
        bookDto2.setType("edu");
        bookDto2.setSummary("x");
        bookDto2.setDateOfPublish(new Date(2004, Calendar.NOVEMBER,23));
        bookDto2.setNumOfPublish(10);
        bookDto2.getAuthors().add(author);
        bookDto2.setPublisher(publisher2);

        author.getBooks().add(bookRepo.save(BookMapper.mapToBook(bookDto)));
        author.getBooks().add(bookRepo.save(BookMapper.mapToBook(bookDto2)));
        author1.getBooks().add(bookRepo.save(BookMapper.mapToBook(bookDto1)));

        List<Book> books=bookRepo.findAllByAuthors(author.getId());
        List<Book> books1=bookRepo.findAllByAuthors(author1.getId());
        Assertions.assertEquals(2,books.size(),"there is wrong in search by Author function");
        Assertions.assertEquals("aws1",books1.getFirst().getAuthors().stream().toList().getFirst().getName(),"there is wrong in search by Author function");

    }

}
