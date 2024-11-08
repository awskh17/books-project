package firsProject.booksProject;

import firsProject.booksProject.BooksProject.BooksProjectTest;
import firsProject.booksProject.BooksProject.ReposTest;
import org.junit.jupiter.api.Test;
import org.junit.runner.notification.RunListener;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class BooksProjectApplicationTests {
	@MockBean
	ReposTest ReposTest;
	@MockBean
	BooksProjectTest booksProjectTest;

	@Test
	void contextLoads() throws Exception {
	ReposTest.deletebook();
	ReposTest.addnewbook();
	ReposTest.findAllBySummary();
	ReposTest.findAllByTitle();
	ReposTest.findByAuthor();
	ReposTest.findByPublisher();

	booksProjectTest.shouldCreateNewBook();
	}

}
