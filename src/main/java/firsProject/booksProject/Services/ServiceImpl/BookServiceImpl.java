package firsProject.booksProject.Services.ServiceImpl;

import firsProject.booksProject.Dtos.BookDto;
import firsProject.booksProject.Entity.Book;
import firsProject.booksProject.Exceptions.BookNotFoundException;
import firsProject.booksProject.Mapper.BookMapper;
import firsProject.booksProject.Repositories.BookRepo;
import firsProject.booksProject.Services.BookService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class BookServiceImpl implements BookService {

    BookRepo bookRepo;

    public BookServiceImpl(BookRepo bookRepo) {
        this.bookRepo = bookRepo;
    }

    @Override
    public BookDto addBook(BookDto bookDto) {
        Book book = BookMapper.mapToBook(bookDto);
        Book savedBook =  bookRepo.save(book);
        return BookMapper.mapToBookDTO(savedBook);
    }

    @Override
    public BookDto updateBookById(BookDto bookDto,long id) {
       Book book= bookRepo.findById(id).orElseThrow(() -> new BookNotFoundException("The Book With Id : "+id+" Is Not Found"));
        bookRepo.delete(book);
       return addBook(bookDto);

    }

    @Override
    public void deleteBook(long id) {
        Book book= bookRepo.findById(id).orElseThrow(() -> new BookNotFoundException("The Book With Id : "+id+" Is Not Found"));
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
        if(book==null) {throw new BookNotFoundException("The Book With Title : "+title+" Is Not Found"); }
        return book.stream().map(BookMapper::mapToBookDTO).toList();
    }

    @Override
    public List<BookDto> getAllBooksByPublisher(String publisher) {
        Set<Book> books=new HashSet<>(bookRepo.findAll());
        Set<Book> res=new HashSet<>();
            for (Book book : books) {
                 if(book.getPublisher().equals(publisher)) {
                    res.add(book);
                }
            }
        if(res.size()==0) {throw new BookNotFoundException("The Book With Publisher : "+publisher+" Is Not Found"); }
        return res.stream().map(b -> BookMapper.mapToBookDTO(b)).toList();
    }

    @Override
    public List<BookDto> getAllBooksByAuthors(List<String> authors) {
        Set<Book> books=new HashSet<>(bookRepo.findAll());
        Set<Book> res=new HashSet<>();
        for(String author : authors) {
            for (Book book : books) {
                for(String author1:book.getAuthors()) {
                  if(author.equals(author1)) {res.add(book);}
                }
            }
        }
        if(books.size()==0) {throw new BookNotFoundException("The Book With Authors : "+authors+" Is Not Found"); }
        return res.stream().map(b -> BookMapper.mapToBookDTO(b)).toList();
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

}
