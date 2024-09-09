package firsProject.booksProject.Repositories;


import firsProject.booksProject.Entity.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MyUserRepo extends JpaRepository<MyUser, Long> {
MyUser findByUsername(String username);
}

