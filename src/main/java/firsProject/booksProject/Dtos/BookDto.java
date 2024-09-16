package firsProject.booksProject.Dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
<<<<<<< HEAD

=======
<<<<<<< Updated upstream
@AllArgsConstructor
>>>>>>> main
@NoArgsConstructor
@Getter
@Setter
=======

>>>>>>> Stashed changes
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

    public BookDto() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Date getDateOfPublish() {
        return dateOfPublish;
    }

    public void setDateOfPublish(Date dateOfPublish) {
        this.dateOfPublish = dateOfPublish;
    }

    public int getNumOfPublish() {
        return numOfPublish;
    }

    public void setNumOfPublish(int numOfPublish) {
        this.numOfPublish = numOfPublish;
    }

    public Set<String> getAuthors() {
        return authors;
    }

    public void setAuthors(Set<String> authors) {
        this.authors = authors;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
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
