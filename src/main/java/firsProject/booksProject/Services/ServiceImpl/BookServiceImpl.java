package firsProject.booksProject.Services.ServiceImpl;

import firsProject.booksProject.Dtos.BookDto;
import firsProject.booksProject.Entity.Author;
import firsProject.booksProject.Entity.Book;
import firsProject.booksProject.Entity.Publisher;
import firsProject.booksProject.Exceptions.BookNotFoundException;
import firsProject.booksProject.Mapper.BookMapper;
import firsProject.booksProject.Repositories.AuthorRepo;
import firsProject.booksProject.Repositories.BookRepo;
import firsProject.booksProject.Repositories.PublisherRepo;
import firsProject.booksProject.Services.BookService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class BookServiceImpl implements BookService {

    BookRepo bookRepo;
    AuthorRepo authorRepo;
    PublisherRepo publisherRepo;

    public BookServiceImpl(BookRepo bookRepo, AuthorRepo authorRepo, PublisherRepo publisherRepo) {
        this.bookRepo = bookRepo;
        this.authorRepo = authorRepo;
        this.publisherRepo = publisherRepo;
    }

    @Override
    public BookDto addBook(BookDto bookDto) {
        Book book = BookMapper.mapToBook(bookDto);

        Set<Author> authors=book.getAuthors();
        authorRepo.saveAll(authors);

        Publisher publisher=book.getPublisher();
        publisherRepo.save(publisher);

        Book savedBook =  bookRepo.save(book);

        publisher.getBooks().add(book);
        publisherRepo.save(publisher);

        for(Author author : authors) {
        author.getBooks().add(book);
        }

        authorRepo.saveAll(authors);
        return BookMapper.mapToBookDTO(savedBook);
    }

    @Override
    public BookDto updateBookById(BookDto bookDto,long id) {
       Book book= bookRepo.findById(id).orElseThrow(() -> new BookNotFoundException("The Book With Id : "+id+" Is Not Found"));
        Publisher publisher=book.getPublisher();
        if(publisher.getBooks().size()==1) publisherRepo.delete(publisher);
        else publisher.getBooks().remove(book);
        Set<Author> authors=book.getAuthors();
        for(Author author : authors) {
            author.getBooks().remove(book);
            if(author.getBooks().size()==0) {authorRepo.delete(author);}
        }
        bookRepo.delete(book);
       return addBook(bookDto);

    }

    @Override
    public void deleteBook(long id) {
        Book book= bookRepo.findById(id).orElseThrow(() -> new BookNotFoundException("The Book With Id : "+id+" Is Not Found"));
        Publisher publisher=book.getPublisher();
        Set<Author> authors=book.getAuthors();
        if(publisher.getBooks().size()==1) publisherRepo.delete(publisher);
        else publisher.getBooks().remove(book);
        for(Author author : authors) {
            author.getBooks().remove(book);
            if(author.getBooks().size()==0) {authorRepo.delete(author);}
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
    public BookDto getBookByTitle(String title) {
        Book book=bookRepo.findByTitle(title);
        if(book==null) {throw new BookNotFoundException("The Book With Title : "+title+" Is Not Found"); }
        return BookMapper.mapToBookDTO(book);
    }

    @Override
    public List<BookDto> getBookByPublisher(Publisher publisher) {
       Publisher publisher1=publisherRepo.findByName(publisher.getName());
        if(publisher1==null) {throw new BookNotFoundException("The Publisher With Name : "+publisher.getName()+" Is Not Found"); }
        List<Book> books=publisher1.getBooks().stream().toList();
        if(books==null) {throw new BookNotFoundException("The Book With Publisher : "+publisher+" Is Not Found"); }
        return books.stream().map(b -> BookMapper.mapToBookDTO(b)).toList();
    }

    @Override
    public List<BookDto> getBookByAuthors(List<Author> authors) {
        List<Book> books=new ArrayList<>();
        for(Author author : authors) {
            author = authorRepo.findByName(author.getName());
            if(author==null) {continue;}
            books.addAll(author.getBooks());
        }
        if(books.size()==0) {throw new BookNotFoundException("The Book With Authors : "+authors+" Is Not Found"); }
        return books.stream().map(b -> BookMapper.mapToBookDTO(b)).toList();
    }

    @Override
    public List<BookDto> getBookBySummary(String summary) {
        List<Book> books =bookRepo.findBySummary(summary);
        if(books==null) {throw new BookNotFoundException("Book not found");}
        return books.stream().map(b -> BookMapper.mapToBookDTO(b)).toList();
    }

    @Override
    public BookDto getBookById(long id) {
        Book book=bookRepo.findBookById(id);
        if(book==null) throw new BookNotFoundException("the book with id "+id+" is not found");
        return BookMapper.mapToBookDTO(book);
    }

}
