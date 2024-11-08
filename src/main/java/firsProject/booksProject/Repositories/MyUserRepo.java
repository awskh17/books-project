package firsProject.booksProject.Repositories;


import firsProject.booksProject.Entity.MyUser;
import firsProject.booksProject.QueryRewriter.MyQueryRewriter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MyUserRepo extends JpaRepository<MyUser, Long> {
    @Query(value ="select * from My_User u where u.username = ?1",nativeQuery = true,
            queryRewriter = MyQueryRewriter.class)
MyUser findByUsername(String username);
}

