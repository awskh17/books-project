package firsProject.booksProject.Repositories;

import firsProject.booksProject.Entity.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PublisherRepo extends JpaRepository<Publisher, Long> {
    Publisher findByName(String name);
}
