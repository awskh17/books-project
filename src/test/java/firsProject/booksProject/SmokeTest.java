package firsProject.booksProject;

import firsProject.booksProject.Controllers.FrontBookController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class SmokeTest {

    @Autowired
    private FrontBookController controller;

    @Test
    void contextLoads()throws Exception{
        assertThat(controller).isNotNull();
    }
}
