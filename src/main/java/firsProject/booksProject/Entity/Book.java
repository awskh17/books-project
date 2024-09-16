package firsProject.booksProject.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

<<<<<<< Updated upstream
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
=======
>>>>>>> Stashed changes
@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String title;
    private String type;
    private String summary;
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "dd-mm-yyyy")
    private Date dateOfPublish;
    private int numOfPublish;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "author_book", joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id"))
    private Set<Author> authors;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "publisher-id")
    private Publisher publisher;


<<<<<<< Updated upstream
=======
    public Book(long id, String title, String type, String summary, Date dateOfPublish, int numOfPublish, Set<String> authors, String publisher) {
        this.id = id;
        this.title = title;
        this.type = type;
        this.summary = summary;
        this.dateOfPublish = dateOfPublish;
        this.numOfPublish = numOfPublish;
        this.authors = authors;
        this.publisher = publisher;
    }

    public Book() {
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
>>>>>>> Stashed changes
}
