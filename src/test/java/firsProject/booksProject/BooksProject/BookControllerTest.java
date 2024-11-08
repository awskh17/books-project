package firsProject.booksProject.BooksProject;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import firsProject.booksProject.Controllers.FrontBookController;
import firsProject.booksProject.Dtos.BookDto;
import firsProject.booksProject.Entity.Author;
import firsProject.booksProject.Entity.Publisher;
import firsProject.booksProject.Security.SecurityConfig;
import firsProject.booksProject.Services.BookService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.test.context.annotation.SecurityTestExecutionListeners;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithSecurityContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import java.util.*;

@WebMvcTest(controllers = FrontBookController.class)
@WithMockUser(username="admin",roles={"ADMIN","USER"})
public class BookControllerTest {



    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FrontBookController bookController;

    @MockBean
    private BookService bookService;
//    Set<String> authorstmp =new HashSet<>();
//    Set<String> authorstmp2 =new HashSet<>();
//    Set<String> authorstmp3 =new HashSet<>();
    @Test
    public void getallbooks() throws Exception {
        BookDto bookDto=new BookDto();
        Author author=new Author();
        Publisher publisher=new Publisher();
        publisher.setName("aws1");
        author.setName("aws");
        bookDto.setTitle("x");
        bookDto.setType("edu");
        bookDto.setSummary("x");
        bookDto.setDateOfPublish(new Date(2004, Calendar.NOVEMBER,23));
        bookDto.setNumOfPublish(10);
        bookDto.getAuthors().add(author);
        bookDto.setPublisher(publisher);

        BookDto bookDto1=new BookDto();
        Author author1=new Author();
        Publisher publisher1=new Publisher();
        publisher1.setName("aws2");
        author1.setName("aws");
        bookDto1.setTitle("xx");
        bookDto1.setType("edu");
        bookDto1.setSummary("x");
        bookDto1.setDateOfPublish(new Date(2004, Calendar.NOVEMBER,23));
        bookDto1.setNumOfPublish(10);
        bookDto1.getAuthors().add(author);
        bookDto1.setPublisher(publisher);

        BookDto bookDto2=new BookDto();
        Author author2=new Author();
        Publisher publisher2=new Publisher();
        publisher2.setName("aws3");
        author2.setName("aws");
        bookDto2.setTitle("xxx");
        bookDto2.setType("edu");
        bookDto2.setSummary("x");
        bookDto2.setDateOfPublish(new Date(2004, Calendar.NOVEMBER,23));
        bookDto2.setNumOfPublish(10);
        bookDto2.getAuthors().add(author);
        bookDto2.setPublisher(publisher);

        when(bookService.getAllBooks()).thenReturn(List.of(bookDto,bookDto1,bookDto2));
        MvcResult mvcResult= mockMvc.perform(get("/front/api/books/get")).andExpect(status().isOk()).andReturn();
        Assertions.assertEquals(200,mvcResult.getResponse().getStatus());
    }
    @Test
    public void addBook() throws Exception {
        BookDto bookDto=new BookDto();
        Author author=new Author();
        Publisher publisher=new Publisher();
        Set<String> authors=new HashSet<>();
        publisher.setName("aws1");
        author.setName("aws");
        bookDto.setTitle("x");
        bookDto.setType("edu");
        bookDto.setSummary("x");
        bookDto.setDateOfPublish(new Date(2004, Calendar.NOVEMBER,23));
        bookDto.setNumOfPublish(10);
        bookDto.setPublisher(publisher);
        when(bookService.addBook(any(BookDto.class))).thenReturn(bookDto);
        bookController.authorstmp.add("aws");
        MvcResult mvcResult = mockMvc.perform(post("/front/api/books/save")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("title", bookDto.getTitle())
                        .param("type", bookDto.getType())
                        .param("summary", bookDto.getSummary())
                        .param("dateOfPublish", "2022-10-19")
                        .param("numOfPublish", String.valueOf(bookDto.getNumOfPublish()))
                        .param("publisher.name", bookDto.getPublisher().getName())
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())  // Expect a 302 redirect status
                .andExpect(redirectedUrl("/front/api/books/get"))  // Redirect to the books list page
                .andReturn();

        Assertions.assertEquals(302,mvcResult.getResponse().getStatus(),"add book form is facing some problem here");
    }

    @Test
    public void updateFormBook() throws Exception {

        BookDto bookDto=new BookDto();
        Author author=new Author();
        Publisher publisher=new Publisher();
        publisher.setName("aws1");
        author.setName("aws");
        bookDto.setTitle("x");
        bookDto.setType("edu");
        bookDto.setSummary("x");
        bookDto.setDateOfPublish(new Date(2004, Calendar.NOVEMBER,23));
        bookDto.setNumOfPublish(10);
        bookDto.getAuthors().add(author);
        bookDto.setPublisher(publisher);

        when(bookService.getBookById(bookDto.getId())).thenReturn(bookDto);
        MvcResult mvcResult= mockMvc.perform(get("/front/api/books/update/{id}",bookDto.getId())).andReturn();
        Assertions.assertEquals(200,mvcResult.getResponse().getStatus(),"update book form is facing some problem here");
    }

    @Test
    public void deletebook() throws Exception {
        BookDto bookDto=new BookDto();
        Author author=new Author();
        Publisher publisher=new Publisher();
        publisher.setName("aws1");
        author.setName("aws");
        bookDto.setTitle("x");
        bookDto.setType("edu");
        bookDto.setSummary("x");
        bookDto.setDateOfPublish(new Date(2004, Calendar.NOVEMBER,23));
        bookDto.setNumOfPublish(10);
        bookDto.getAuthors().add(author);
        bookDto.setPublisher(publisher);

        when(bookService.deleteBook(bookDto.getId())).thenReturn(bookDto);
        MvcResult mvcResult= mockMvc.perform(get("/front/api/books/deleteBook/{id}",bookDto.getId()).with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andReturn();
        Assertions.assertEquals(302,mvcResult.getResponse().getStatus());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}