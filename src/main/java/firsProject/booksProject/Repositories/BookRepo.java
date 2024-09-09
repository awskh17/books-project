package firsProject.booksProject.Repositories;

import firsProject.booksProject.Entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepo extends JpaRepository<Book, Long> {
Book findByTitle(String title);
List<Book> findBySummary(String summary);
}
