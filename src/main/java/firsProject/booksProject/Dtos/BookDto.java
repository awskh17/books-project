package firsProject.booksProject.Dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import firsProject.booksProject.Entity.Author;
import firsProject.booksProject.Entity.Publisher;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.Set;
<<<<<<< Updated upstream
@AllArgsConstructor
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
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "dd-mm-yyyy")
    private Date dateOfPublish;
    private int numOfPublish;
    private Set<Author> authors;
    private Publisher publisher;

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
