package firsProject.booksProject.Repositories;

import firsProject.booksProject.Entity.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PublisherRepo extends JpaRepository<Publisher,Long> {
   List<Publisher> findAllByName(String name);
}
