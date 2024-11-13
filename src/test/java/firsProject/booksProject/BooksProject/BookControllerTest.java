package firsProject.booksProject.BooksProject;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import firsProject.booksProject.Controllers.FrontBookController;
import firsProject.booksProject.Dtos.BookDto;
import firsProject.booksProject.Entity.Author;
import firsProject.booksProject.Entity.Publisher;
import firsProject.booksProject.Services.BookService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.thymeleaf.spring6.expression.Mvc;

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
    static BookDto bookDto=new BookDto();
    static BookDto bookDto1=new BookDto();
    static BookDto bookDto2=new BookDto();
    static Author author=new Author();
    static Publisher publisher=new Publisher();
    static Author author1=new Author();
    static Publisher publisher1=new Publisher();
    static Author author2=new Author();
    static Publisher publisher2=new Publisher();
    @BeforeAll
    static void setUp(){
        publisher.setName("aws1");
        author.setName("aws");
        bookDto.setTitle("x");
        bookDto.setType("edu");
        bookDto.setSummary("x");
        bookDto.setDateOfPublish(new Date(2004, Calendar.NOVEMBER,23));
        bookDto.setNumOfPublish(10);
        bookDto.getAuthors().add(author);
        bookDto.setPublisher(publisher);

        publisher1.setName("aws2");
        author1.setName("aws");
        bookDto1.setTitle("xx");
        bookDto1.setType("edu");
        bookDto1.setSummary("x");
        bookDto1.setDateOfPublish(new Date(2004, Calendar.NOVEMBER,23));
        bookDto1.setNumOfPublish(10);
        bookDto1.getAuthors().add(author);
        bookDto1.setPublisher(publisher);

        publisher2.setName("aws3");
        author2.setName("aws");
        bookDto2.setTitle("xxx");
        bookDto2.setType("edu");
        bookDto2.setSummary("x");
        bookDto2.setDateOfPublish(new Date(2004, Calendar.NOVEMBER,23));
        bookDto2.setNumOfPublish(10);
        bookDto2.getAuthors().add(author);
        bookDto2.setPublisher(publisher);
    }

    @Test
    void indexpage() throws Exception {
        MvcResult mvcResult=mockMvc.perform(get("/")).andReturn();
        Assertions.assertEquals(200,mvcResult.getResponse().getStatus(),"index page has error");
    }

    @Test
    public void getallbooks() throws Exception {
        when(bookService.getAllBooks()).thenReturn(List.of(bookDto,bookDto1,bookDto2));
        MvcResult mvcResult= mockMvc.perform(get("/front/api/books/get")).andExpect(status().isOk()).andReturn();
        Assertions.assertEquals(200,mvcResult.getResponse().getStatus());
    }

    @Test
    void addPage () throws Exception{
        MvcResult mvcResult=mockMvc.perform(get("/front/api/books/add")).andReturn();
        Assertions.assertEquals(200,mvcResult.getResponse().getStatus(),"add page has error");
    }

    @Test
    void updateForm () throws Exception{
        when(bookService.getBookById(bookDto.getId())).thenReturn(bookDto);
        MvcResult mvcResult=mockMvc.perform(get("/front/api/books/update/{id}",bookDto.getId())).andReturn();
        Assertions.assertEquals(200,mvcResult.getResponse().getStatus(),"updateForm has error");
    }

    @Test
    void modifyBook() throws Exception{
        when(bookService.updateBookById(bookDto,1)).thenReturn(bookDto);
        MvcResult mvcResult = mockMvc.perform(post("/front/api/books/modify/{id}",1)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("title", bookDto.getTitle())
                        .param("type", bookDto.getType())
                        .param("summary", bookDto.getSummary())
                        .param("dateOfPublish", "2022-10-19")
                        .param("numOfPublish", String.valueOf(bookDto.getNumOfPublish()))
                        .param("publisher.name", bookDto.getPublisher().getName())
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/front/api/books/get"))
                .andReturn();

        Assertions.assertEquals(302,mvcResult.getResponse().getStatus(),"add book form is facing some problem here");
    }

    @Test
    public void addAuthor () throws Exception{
        MvcResult mvcResult = mockMvc.perform(post("/front/api/books/addAuthor")
                        .param("author","AddedAuthor").with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/front/api/books/add"))
                .andReturn();
        Assertions.assertEquals(302,mvcResult.getResponse().getStatus(),"add author is facing some problem here");
    }

    @Test
    public void addAuthorForModify () throws Exception{
        MvcResult mvcResult = mockMvc.perform(post("/front/api/books/modify/addAuthor/{id}",1)
                        .param("author","AddedAuthorForModify").with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/front/api/books/update/1"))
                .andReturn();
        Assertions.assertEquals(302,mvcResult.getResponse().getStatus(),"add Author for modify form is facing some problem here");
    }

    @Test
    public void saveBook() throws Exception {
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
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/front/api/books/get"))
                .andReturn();

        Assertions.assertEquals(302,mvcResult.getResponse().getStatus(),"add book form is facing some problem here");
    }

    @Test
    public void deletebook() throws Exception {
        when(bookService.deleteBook(bookDto.getId())).thenReturn(bookDto);
        MvcResult mvcResult= mockMvc.perform(get("/front/api/books/deleteBook/{id}",bookDto.getId()).with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andReturn();
        Assertions.assertEquals(302,mvcResult.getResponse().getStatus());
    }

    @Test
    public void updateFormBook() throws Exception {
        when(bookService.getBookById(bookDto.getId())).thenReturn(bookDto);
        MvcResult mvcResult= mockMvc.perform(get("/front/api/books/update/{id}",bookDto.getId())).andReturn();
        Assertions.assertEquals(200,mvcResult.getResponse().getStatus(),"update book form is facing some problem here");
    }

    @Test
    public void delAuthor () throws Exception{
        bookController.authorstmp.add("aws");
        MvcResult mvcResult = mockMvc.perform(get("/front/api/books/delAuthor/{author}","aws"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/front/api/books/add"))
                .andReturn();
        Assertions.assertEquals(0,bookController.authorstmp.size(),"the delAuth doesn't delete");
        Assertions.assertEquals(302,mvcResult.getResponse().getStatus(),"del Author form is facing some problem here");
    }

    @Test
    public void delAuthorForSearch () throws Exception{
        bookController.authorstmp2.add("aws");
        MvcResult mvcResult = mockMvc.perform(get("/front/api/books/delAuthor/search/{author}","aws"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/front/api/books/searchAuthor"))
                .andReturn();
        Assertions.assertEquals(0,bookController.authorstmp2.size(),"the delAuth Search doesn't delete");
        Assertions.assertEquals(302,mvcResult.getResponse().getStatus(),"delAuth for Search form is facing some problem here");
    }

    @Test
    public void delAuthorForModify () throws Exception{
        bookController.authorstmp3.add("aws");
        MvcResult mvcResult = mockMvc.perform(get("/front/api/books/delAuthor/{author}/{id}","aws",99))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/front/api/books/update/99"))
                .andReturn();
        Assertions.assertEquals(0,bookController.authorstmp3.size(),"the delAuth Modify doesn't delete");
        Assertions.assertEquals(302,mvcResult.getResponse().getStatus(),"delAuth for Modify for modify form is facing some problem here");
    }

    @Test
    public void getpublisher() throws Exception{
        MvcResult mvcResult=mockMvc.perform(get("/front/api/books/searchPublisher")).andReturn();
        Assertions.assertEquals(200,mvcResult.getResponse().getStatus(),"get publisher page doesnt working well");
    }

    @Test
    public void findBypublisher() throws Exception{
        MvcResult mvcResult=mockMvc.perform(post("/front/api/books/findByPublisher")
                        .param("publisherName","aws").with(csrf()))
                .andExpect(redirectedUrl("getBookByPublisher/aws"))
                .andReturn();
        Assertions.assertEquals(302,mvcResult.getResponse().getStatus(),"find book by publisher page doesnt working well");
    }

    @Test
    public void findBookByPublisher() throws Exception{
        when(bookService.getAllBooksByPublisher("aws")).thenReturn(List.of(bookDto,bookDto1));
        MvcResult mvcResult=mockMvc.perform(get("/front/api/books/getBookByPublisher/{publisherName}","aws"))
                .andExpect(model().attribute("book", hasSize(2)))
                .andReturn();
        Assertions.assertEquals(200,mvcResult.getResponse().getStatus(),"find All Books By Publisher");
    }
    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}