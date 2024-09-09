package firsProject.booksProject.Repositories;

import firsProject.booksProject.Entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepo extends JpaRepository<Author, Long> {
Author findByName(String firstName);
}
