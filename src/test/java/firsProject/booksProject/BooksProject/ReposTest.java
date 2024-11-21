package firsProject.booksProject.BooksProject;

import firsProject.booksProject.Dtos.BookDto;
import firsProject.booksProject.Entity.Author;
import firsProject.booksProject.Entity.Book;
import firsProject.booksProject.Entity.Publisher;
import firsProject.booksProject.Mapper.BookMapper;
import firsProject.booksProject.Repositories.AuthorRepo;
import firsProject.booksProject.Repositories.BookRepo;
import firsProject.booksProject.Repositories.PublisherRepo;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@WithMockUser(username="admin",roles={"ADMIN"})
@DataJpaTest

public class ReposTest {


    @Autowired
    private BookRepo bookRepo;
    @Autowired
    private PublisherRepo publisherRepo;
    @Autowired
    private AuthorRepo authorRepo;


      BookDto bookDto=new BookDto();
      Author author=new Author();
      Publisher publisher=new Publisher();
      BookDto bookDto1=new BookDto();
      Author author1=new Author();
      Publisher publisher1=new Publisher();
      BookDto bookDto2=new BookDto();
      Author author2=new Author();
      Publisher publisher2=new Publisher();

    @BeforeEach
     void setUp(){
        publisher.setName("aws1");
        author.setName("aws");
        bookDto.setTitle("x");
        bookDto.setType("edu");
        bookDto.setSummary("x");
        bookDto.setDateOfPublish(new Date(2004, Calendar.NOVEMBER,23));
        bookDto.setNumOfPublish(10);
        bookDto.getAuthors().add(author);
        bookDto.setPublisher(publisher);

        publisher1.setName("aws2");
        author1.setName("aws");
        bookDto1.setTitle("xx");
        bookDto1.setType("edu");
        bookDto1.setSummary("x");
        bookDto1.setDateOfPublish(new Date(2004, Calendar.NOVEMBER,23));
        bookDto1.setNumOfPublish(10);
        bookDto1.getAuthors().add(author);
        bookDto1.setPublisher(publisher);

        publisher2.setName("aws3");
        author2.setName("aws");
        bookDto2.setTitle("xxx");
        bookDto2.setType("edu");
        bookDto2.setSummary("x");
        bookDto2.setDateOfPublish(new Date(2004, Calendar.NOVEMBER,23));
        bookDto2.setNumOfPublish(10);
        bookDto2.getAuthors().add(author);
        bookDto2.setPublisher(publisher);

//        publisherRepo.saveAll(List.of(publisher,publisher1,publisher2));
//        authorRepo.saveAll(List.of(author,author1,author2));
    }

    @Test
    public void addnewbook() throws Exception{


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
        bookRepo.save(BookMapper.mapToBook(bookDto));
        bookRepo.delete(BookMapper.mapToBook(bookDto));
        Assertions.assertEquals(bookRepo.findBookById(bookDto.getId()),null,"something wrong with delete method");
    }
    @Test
    public void findAllBySummary(){
        bookRepo.save(BookMapper.mapToBook(bookDto));
        bookRepo.save(BookMapper.mapToBook(bookDto1));
        bookRepo.save(BookMapper.mapToBook(bookDto2));

        List<Book> books=bookRepo.findAllBySummary("x");
        Assertions.assertEquals(3,books.size(),"there is wrong in search by summary function");
    }

    @Test
    public void findAllByTitle(){
        bookRepo.save(BookMapper.mapToBook(bookDto));
        bookRepo.save(BookMapper.mapToBook(bookDto1));
        bookRepo.save(BookMapper.mapToBook(bookDto2));

        List<Book> books=bookRepo.findAllByTitle("x");
        Assertions.assertEquals(1,books.size(),"there is wrong in search by title function");
    }
    @Test
    public void findByPublisher(){
        publisherRepo.save(publisher);
        publisherRepo.save(publisher1);
        publisherRepo.save(publisher2);

        bookDto.setPublisher(publisher);
        bookDto1.setPublisher(publisher1);
        bookDto2.setPublisher(publisher2);

        publisher.getBooks().add(bookRepo.save(BookMapper.mapToBook(bookDto)));
        publisher1.getBooks().add(bookRepo.save(BookMapper.mapToBook(bookDto1)));
        publisher2.getBooks().add(bookRepo.save(BookMapper.mapToBook(bookDto2)));

        System.out.println(bookRepo.findAllByPublisher(publisher1.getId()));
        List<Book> books1=bookRepo.findAllByPublisher(publisher1.getId());
        List<Book> books2=bookRepo.findAllByPublisher(publisher2.getId());
        Assertions.assertEquals(1,books1.size(),"there is wrong in search by publisher function");
        Assertions.assertEquals("aws3",books2.getFirst().getPublisher().getName(),"there is wrong in search by publisher function");
    }
    @Test
    public void findByAuthor(){
        authorRepo.save(author);
        authorRepo.save(author1);

        bookDto.getAuthors().clear();
        bookDto.getAuthors().add(author);
        bookDto1.getAuthors().clear();
        bookDto1.getAuthors().add(author);
        bookDto2.getAuthors().clear();
        bookDto2.getAuthors().add(author1);

        author.getBooks().add(bookRepo.save(BookMapper.mapToBook(bookDto)));
        author.getBooks().add(bookRepo.save(BookMapper.mapToBook(bookDto2)));
        author1.getBooks().add(bookRepo.save(BookMapper.mapToBook(bookDto1)));
        List<Book> books=bookRepo.findAllByAuthors(author.getId());
        List<Book> books1=bookRepo.findAllByAuthors(author1.getId());
        System.out.println(books1.getFirst().getAuthors().stream().toList().stream().map(a ->a.getName()));
        Assertions.assertEquals(2,books.size(),"there is wrong in search by Author function");
        Assertions.assertEquals("aws",books1.getFirst().getAuthors().stream().toList().getFirst().getName(),"there is wrong in search by Author function");

    }

}
