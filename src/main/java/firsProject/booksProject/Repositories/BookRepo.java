package firsProject.booksProject.Repositories;

import firsProject.booksProject.Entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepo extends JpaRepository<Book, Long> {
List<Book> findAllBySummary(String summary);
List<Book> findAllByTitle(String title);
Book findBookById(long id);

}
