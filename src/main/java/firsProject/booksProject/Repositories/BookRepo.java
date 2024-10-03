package firsProject.booksProject.Repositories;

import firsProject.booksProject.Entity.Author;
import firsProject.booksProject.Entity.Book;
import firsProject.booksProject.Entity.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface BookRepo extends JpaRepository<Book, Long> {
List<Book> findAllBySummary(String summary);
List<Book> findAllByTitle(String title);
List<Book> findAllByPublisher(Publisher publisher);
List<Book> findAllByAuthors(Author author);
Book findBookById(long id);
}
