package firsProject.booksProject.Services.ServiceImpl;

import firsProject.booksProject.Dtos.BookDto;
import firsProject.booksProject.Entity.*;
import firsProject.booksProject.Exceptions.BookNotFoundException;
import firsProject.booksProject.Exceptions.UserFoundException;
import firsProject.booksProject.Jinq.JinqSource;
import firsProject.booksProject.Mapper.BookMapper;
import firsProject.booksProject.Repositories.AuthorRepo;
import firsProject.booksProject.Repositories.BookRepo;
import firsProject.booksProject.Repositories.MyUserRepo;
import firsProject.booksProject.Repositories.PublisherRepo;
import firsProject.booksProject.Services.BookService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.jinq.orm.stream.JinqStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    EntityManager em;
    @Autowired
    JinqSource jinqSource;
    @Autowired
    BookRepo bookRepo;
    @Autowired
    PublisherRepo publisherRepo;
    @Autowired
    AuthorRepo authorRepo;
    @Autowired
    MyUserRepo userRepo;


    @Override
    public BookDto addBook(BookDto bookDto) {
        Book book = BookMapper.mapToBook(bookDto);
        Publisher publisher=null;
        String publisherName=bookDto.getPublisher().getName();
        //add Jinq
        List<Publisher> publishers = jinqSource.streamAll(em, Publisher.class)
                .where(p -> p.getName().equals(publisherName))
                .toList();
        //
        if(publishers.isEmpty())
        {
            publisherRepo.save(bookDto.getPublisher());
        }
        publisher= jinqSource.streamAll(em, Publisher.class)
                .where(p -> p.getName().equals(publisherName))
                .toList().getFirst();
        book.setPublisher(publisher);
        publisher.getBooks().add(book);

        List <Author> authors=book.getAuthors().stream().toList();
        book.getAuthors().clear();
        for(Author author:authors)
        { String authorname=author.getName();
            Optional<Author> author1= jinqSource.streamAll(em,Author.class).where(a -> a.getName().equals(authorname)).findFirst();
            if(author1.isEmpty())
               authorRepo.save(author);
            author.getBooks().add(book);
            Author author2= jinqSource.streamAll(em,Author.class).where(a -> a.getName().equals(authorname)).toList().getFirst();
            book.getAuthors().add(author2);
        }
        Book savedBook =  bookRepo.save(book);
        return BookMapper.mapToBookDTO(savedBook);
    }

    @Override
    public BookDto updateBookById(BookDto bookDto,long id) {
        Book book=new Book();
       List<Book> books=jinqSource.streamAll(em,Book.class).where(b ->b.getId()==id).toList();
       if(books!=null) book=books.getFirst();
       book.setSummary(bookDto.getSummary());
       book.setType(bookDto.getType());
       book.setTitle(bookDto.getTitle());
       book.setDateOfPublish(bookDto.getDateOfPublish());
       book.setNumOfPublish(bookDto.getNumOfPublish());
       //publisher
        long pId=book.getPublisher().getId();
        Publisher publisher=jinqSource.streamAll(em,Publisher.class).where(p->p.getId()==pId).toList().getFirst();
        publisher.getBooks().remove(book);
        publisherRepo.save(publisher);

        String publisherName=bookDto.getPublisher().getName();
        List<Publisher> publishers = jinqSource.streamAll(em, Publisher.class)
                .where(p -> p.getName().equals(publisherName))
                .toList();
        if(publishers.isEmpty())
        {
            publisherRepo.save(bookDto.getPublisher());
        }
        publisher= jinqSource.streamAll(em, Publisher.class)
                .where(p -> p.getName().equals(publisherName))
                .toList().getFirst();

        book.setPublisher(publisher);
        publisher.getBooks().add(book);
        //author
        Set<Author> authors=book.getAuthors();
        for(Author author:authors)
        {  long authId= author.getId();
            author=jinqSource.streamAll(em,Author.class)
                    .where(a-> a.getId()==authId).toList().getFirst();
            author.getBooks().remove(book);
            authorRepo.save(author);
        }
        authors=bookDto.getAuthors();
        book.getAuthors().clear();
        for(Author author:authors)
        {  String tmp=author.getName();
            List<Author> author1=jinqSource.streamAll(em,Author.class).where(a->a.getName().equals(tmp)).toList();
            if(author1.isEmpty()) authorRepo.save(author);
            author=jinqSource.streamAll(em,Author.class).where(a->a.getName().equals(tmp)).toList().getFirst();
            book.getAuthors().add(author);
            author.getBooks().add(book);
        }
        Book savedBook=bookRepo.save(book);
        return BookMapper.mapToBookDTO(savedBook);
    }

    @Override
    public BookDto deleteBook(long id) {
        List <Book> books =jinqSource.streamAll(em,Book.class).where(b->b.getId()==id).toList();
        if(books.isEmpty()) throw new BookNotFoundException("The Book With Id : "+id+" Is Not Found");
        Book book=books.getFirst();
        Publisher publisher=book.getPublisher();
        Set <Author> authors=book.getAuthors();
        if(publisher.getBooks().size()==1) publisherRepo.delete(publisher);
        else publisher.getBooks().remove(book);
        for(Author author:authors)
        {if(author.getBooks().size()==1) authorRepo.delete(author);
            else author.getBooks().remove(book);
        }
        bookRepo.deleteById(book.getId());
        return BookMapper.mapToBookDTO(book);
    }

    @Override
    public List<BookDto> getAllBooks() {

        List<Book> books=jinqSource.streamAll(em,Book.class).toList();
        List<BookDto> bookDtos=books.stream().map(b ->BookMapper.mapToBookDTO(b)).toList();
        return bookDtos;
    }

    @Override
    public List<BookDto> getAllBooksByTitle(String title) {
        List<Book> books=jinqSource.streamAll(em,Book.class).where(b-> Objects.equals(b.getTitle(), title)).toList();
//      if(book==null) {throw new BookNotFoundException("The Book With Title : "+title+" Is Not Found"); }
        return books.stream().map(BookMapper::mapToBookDTO).toList();
    }

    @Override
    public List<BookDto> getAllBooksByPublisher(String publisher) {
        List<Book> books=new ArrayList<>();
        List<Publisher> publisher1=jinqSource.streamAll(em, Publisher.class).where(p-> Objects.equals(p.getName(), publisher)).toList();
        if(!publisher1.isEmpty())
        books=bookRepo.findAllByPublisher(publisher1.getFirst().getId());
        return books.stream().map(BookMapper::mapToBookDTO).toList();
    }

    @Override
    public List<BookDto> getAllBooksByAuthors(List<String> authors) {
        Set<Book> books=new HashSet<>();
        Set <Author> authors1=new HashSet<>();
        for(String name:authors) {
           List<Author> a= jinqSource.streamAll(em, Author.class).where(au-> Objects.equals(au.getName(), name)).toList();
            if(!a.isEmpty())
            authors1.add(a.getFirst());
        }
        for(Author author:authors1)
            books.addAll(jinqSource.streamAll(em,Book.class).where(b->b.getAuthors().contains(author)).toList());
        return books.stream().map(BookMapper::mapToBookDTO).toList();
    }

    @Override
    public List<BookDto> getBookBySummary(String summary) {
        List<Book> books=jinqSource.streamAll(em,Book.class).where(b-> Objects.equals(b.getTitle(), summary)).toList();
        //if(books==null) {throw new BookNotFoundException("Book not found");}
        return books.stream().map(b -> BookMapper.mapToBookDTO(b)).toList();
    }

    @Override
    public BookDto getBookById(long id) {
        Book book=bookRepo.findBookById(id);
        //if(book==null) throw new BookNotFoundException("the book with id "+id+" is not found");
        return BookMapper.mapToBookDTO(book);
    }

    @Override
    public MyUser adduser(String name, String pass) {
        if(!jinqSource.streamAll(em, MyUser.class)
                .where(user-> Objects.equals(user.getUsername(), name)).toList().isEmpty())
                    throw new UserFoundException("there is another user with same username change it please");
        MyUser user=new MyUser();
        user.setUsername(name);
        user.setPassword(passwordEncoder().encode(pass));
        user.setRole("USER");
        return userRepo.save(user);
    }

    @Override
    public List<BookDto> searchpro(SearchPro sp, Set<String> as) {
        Set<Author> authors = new HashSet<>();
        // Populate authors set
        for (String a : as) {
            List<Author> author = jinqSource.streamAll(em, Author.class)
                    .where(au -> au.getName().equals(a))
                    .toList();
            if (!author.isEmpty()) {
                authors.add(author.get(0)); // Add the first author to the set
            }
        }

        String title = sp.getTitle();
        String type = sp.getType();
        String summary = sp.getSummary();
        String publisher = sp.getPublisher();
        Date d = sp.getDateOfPublish();
        Date d1 = sp.getDateOfPublish1();
        // Query books with simplified conditions
        Set<Book> books= new HashSet<>(jinqSource.streamAll(em, Book.class)
                .where(b -> title == "" || b.getTitle().equals(title)) // Title check
                .where(b -> type == "" || b.getType().equals(type)) // Type check
                .where(b -> summary == "" || b.getSummary().equals(summary)) // Summary check
                .where(b -> d == null || b.getDateOfPublish().after(d)) // Filter by publish date
                .where(b -> d1 == null || b.getDateOfPublish().before(d1)) // Filter by publish date
                .where(b -> publisher == "" || b.getPublisher().getName().equals(publisher)) // Publisher check
                .toList());
        Set<Book> books1= new HashSet<>();
        if(!as.isEmpty())
        for(Author author:authors)
        {books1.addAll(jinqSource.streamAll(em,Book.class).where(b->b.getAuthors().contains(author)).toList()); books.retainAll(books1); }


        return books.stream().map(BookMapper::mapToBookDTO).toList();
    }


    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
