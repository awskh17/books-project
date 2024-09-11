package firsProject.booksProject.Dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import firsProject.booksProject.Entity.Author;
import firsProject.booksProject.Entity.Publisher;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.Set;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BookDto {
    private long id;
    private String title;
    private String type;
    private String summary;
    //@JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "dd-mm-yyyy")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateOfPublish;
    private int numOfPublish;
    private Set<Author> authors;
    private Publisher publisher;

    @Override
    public String toString() {
        return "BookDto{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", type='" + type + '\'' +
                ", summary='" + summary + '\'' +
                ", dateOfPublish=" + dateOfPublish +
                ", numOfPublish=" + numOfPublish +
                ", authors=" + authors +
                ", publisher=" + publisher +
                '}';
    }
}
