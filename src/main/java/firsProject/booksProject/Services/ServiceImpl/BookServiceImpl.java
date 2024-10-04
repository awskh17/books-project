package firsProject.booksProject.Services.ServiceImpl;

import firsProject.booksProject.Dtos.BookDto;
import firsProject.booksProject.Entity.Author;
import firsProject.booksProject.Entity.Book;
import firsProject.booksProject.Entity.MyUser;
import firsProject.booksProject.Entity.Publisher;
import firsProject.booksProject.Exceptions.BookNotFoundException;
import firsProject.booksProject.Mapper.BookMapper;
import firsProject.booksProject.Repositories.AuthorRepo;
import firsProject.booksProject.Repositories.BookRepo;
import firsProject.booksProject.Repositories.MyUserRepo;
import firsProject.booksProject.Repositories.PublisherRepo;
import firsProject.booksProject.Services.BookService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.NotAcceptableStatusException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class BookServiceImpl implements BookService {

    BookRepo bookRepo;
    PublisherRepo publisherRepo;
    AuthorRepo authorRepo;
    MyUserRepo userRepo;

    public BookServiceImpl(BookRepo bookRepo, PublisherRepo publisherRepo, AuthorRepo authorRepo, MyUserRepo userRepo) {
        this.bookRepo = bookRepo;
        this.publisherRepo = publisherRepo;
        this.authorRepo = authorRepo;
        this.userRepo = userRepo;
    }

    @Override
    public BookDto addBook(BookDto bookDto) {
        Book book = BookMapper.mapToBook(bookDto);
        Publisher publisher=null;
        if(publisherRepo.findByName(bookDto.getPublisher().getName())==null)
        {
            publisherRepo.save(bookDto.getPublisher());
        }
        publisher=publisherRepo.findByName(bookDto.getPublisher().getName());
        book.setPublisher(publisher);
        publisher.getBooks().add(book);

        List <Author> authors=book.getAuthors().stream().toList();
        book.getAuthors().clear();
        for(Author author:authors)
        {
            if(authorRepo.findByName(author.getName())==null)
               authorRepo.save(author);
            author.getBooks().add(book);
            book.getAuthors().add(authorRepo.findByName(author.getName()));
        }
        Book savedBook =  bookRepo.save(book);
        return BookMapper.mapToBookDTO(savedBook);
    }

    @Override
    public BookDto updateBookById(BookDto bookDto,long id) {
       Book book= bookRepo.findById(id).orElseThrow(() -> new BookNotFoundException("The Book With Id : "+id+" Is Not Found"));
       if(book!=null) book=bookRepo.getReferenceById(id);
       book.setSummary(bookDto.getSummary());
       book.setType(bookDto.getType());
       book.setTitle(bookDto.getTitle());
       book.setDateOfPublish(bookDto.getDateOfPublish());
       book.setNumOfPublish(bookDto.getNumOfPublish());
       //publisher
        Publisher publisher=publisherRepo.getReferenceById(book.getPublisher().getId());
        publisher.getBooks().remove(book);
        publisherRepo.save(publisher);
        if(publisherRepo.findByName(bookDto.getPublisher().getName())==null)
        {
            publisherRepo.save(bookDto.getPublisher());
        }
        publisher=publisherRepo.findByName(bookDto.getPublisher().getName());
        book.setPublisher(publisher);
        publisher.getBooks().add(book);
        //author
        Set<Author> authors=book.getAuthors();
        for(Author author:authors)
        {
            author=authorRepo.getReferenceById(author.getId());
            author.getBooks().remove(book);
            authorRepo.save(author);
        }
        authors=bookDto.getAuthors();
        book.getAuthors().clear();
        for(Author author:authors)
        {
            if(authorRepo.findByName(author.getName())==null) authorRepo.save(author);
            author=authorRepo.findByName(author.getName());
            book.getAuthors().add(author);
            author.getBooks().add(book);
        }
        Book savedBook=bookRepo.save(book);
        return BookMapper.mapToBookDTO(savedBook);
    }

    @Override
    public void deleteBook(long id) {
        Book book= bookRepo.findById(id).orElseThrow(() -> new BookNotFoundException("The Book With Id : "+id+" Is Not Found"));
        Publisher publisher=book.getPublisher();
        Set <Author> authors=book.getAuthors();
        if(publisher.getBooks().size()==1) publisherRepo.delete(publisher);
        else publisher.getBooks().remove(book);
        for(Author author:authors)
        {if(author.getBooks().size()==1) authorRepo.delete(author);
            else author.getBooks().remove(book);
        }
        bookRepo.delete(book);
    }

    @Override
    public List<BookDto> getAllBooks() {
        List<Book> books=bookRepo.findAll();
        List<BookDto> bookDtos=books.stream().map(b ->BookMapper.mapToBookDTO(b)).toList();
        return bookDtos;
    }

    @Override
    public List<BookDto> getAllBooksByTitle(String title) {
        List<Book> book=bookRepo.findAllByTitle(title);
//        if(book==null) {throw new BookNotFoundException("The Book With Title : "+title+" Is Not Found"); }
        return book.stream().map(BookMapper::mapToBookDTO).toList();
    }

    @Override
    public List<BookDto> getAllBooksByPublisher(String publisher) {
        List<Book> books=new ArrayList<>();
        Publisher publisher1=publisherRepo.findByName(publisher);
        books=bookRepo.findAllByPublisher(publisher1);
        return books.stream().map(BookMapper::mapToBookDTO).toList();
    }

    @Override
    public List<BookDto> getAllBooksByAuthors(List<String> authors) {
        Set<Book> books=new HashSet<>();
        Set <Author> authors1=new HashSet<>();
        for(String name:authors) {
            Author a=authorRepo.findByName(name);
            if(a!=null)
            authors1.add(a);
        }
        for(Author author:authors1)
            books.addAll(bookRepo.findAllByAuthors(author));
        return books.stream().map(BookMapper::mapToBookDTO).toList();
    }

    @Override
    public List<BookDto> getBookBySummary(String summary) {
        List<Book> books =bookRepo.findAllBySummary(summary);
        if(books==null) {throw new BookNotFoundException("Book not found");}
        return books.stream().map(b -> BookMapper.mapToBookDTO(b)).toList();
    }

    @Override
    public BookDto getBookById(long id) {
        Book book=bookRepo.findBookById(id);
        if(book==null) throw new BookNotFoundException("the book with id "+id+" is not found");
        return BookMapper.mapToBookDTO(book);
    }

    @Override
    public CommandLineRunner userup(String name, String pass) {
        return args -> {
            MyUser user1=new MyUser();
            user1.setUsername(name);
            user1.setPassword(passwordEncoder().encode(pass));
            user1.setRoles(Set.of("USER"));
            if(userRepo.findByUsername(name)==null)
            userRepo.save(user1);
            else throw new NotAcceptableStatusException("this username is token before,please try another name");
        };
    }
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
