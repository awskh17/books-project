package firsProject.booksProject.Repositories;

import firsProject.booksProject.Entity.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PublisherRepo extends JpaRepository<Publisher, Long> {
    Publisher findByName(String name);
    Publisher findByid(Long id);

    List<Publisher> findAll();
}
