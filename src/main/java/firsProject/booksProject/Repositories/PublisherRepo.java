package firsProject.booksProject.Repositories;

import firsProject.booksProject.Entity.Publisher;
import firsProject.booksProject.QueryRewriter.MyQueryRewriter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PublisherRepo extends JpaRepository<Publisher,Long> {
   @Query(value ="select * from publisher p where p.name = ?1",nativeQuery = true,
           queryRewriter = MyQueryRewriter.class)
   Publisher findByName(String name);
}
