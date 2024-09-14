package firsProject.booksProject.Dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
public class BookDto {
    private long id;
    private String title;
    private String type;
    private String summary;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateOfPublish;
    private int numOfPublish;
    private Set<String> authors=new HashSet<>();
    private String publisher;

    public BookDto(long id, String title, String type, String summary, Date dateOfPublish, int numOfPublish, Set<String> authors, String publisher) {
        this.id = id;
        this.title = title;
        this.type = type;
        this.summary = summary;
        this.dateOfPublish = dateOfPublish;
        this.numOfPublish = numOfPublish;
        this.authors = authors;
        this.publisher = publisher;
    }

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
