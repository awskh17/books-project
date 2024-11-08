package firsProject.booksProject.Repositories;

import firsProject.booksProject.Entity.Book;
import firsProject.booksProject.QueryRewriter.MyQueryRewriter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepo extends JpaRepository<Book, Long>{

@Query(value ="select * from Book",nativeQuery = true,
        queryRewriter = MyQueryRewriter.class)
List<Book> findAll();
    @Query(value ="select * from Book b where b.summary = ?1",nativeQuery = true,
            queryRewriter = MyQueryRewriter.class)
List<Book> findAllBySummary(String summary);
    @Query(value ="select * from Book b where b.title = ?1",nativeQuery = true,
            queryRewriter = MyQueryRewriter.class)
List<Book> findAllByTitle(String title);
    @Query(value ="select * from Book b where publisher_id = ?1",nativeQuery = true,
            queryRewriter = MyQueryRewriter.class)
List<Book> findAllByPublisher(Long id);
    @Query(value ="select * from Book b inner join book_author ba on ba.Book_id=b.id where ba.author_id=?1",nativeQuery = true,
            queryRewriter = MyQueryRewriter.class)
List<Book> findAllByAuthors(long id);
    @Query(value ="select * from Book b where b.id = ?1",nativeQuery = true,
            queryRewriter = MyQueryRewriter.class)
Book findBookById(long id);
}
