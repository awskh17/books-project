package firsProject.booksProject.Repositories;

import firsProject.booksProject.Entity.Author;
import firsProject.booksProject.QueryRewriter.MyQueryRewriter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryRewriter;

public interface AuthorRepo extends JpaRepository<Author,Long> {

    @Query(value ="select * from Author a where a.name = ?1",nativeQuery = true,
            queryRewriter = MyQueryRewriter.class)
    Author  findByName(String name);
}
