package firsProject.booksProject.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String title;
    private String type;
    private String summary;
    //@JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "dd-mm-yyyy")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateOfPublish;
    private int numOfPublish;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "author_book", joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id"))
    private Set<Author> authors;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "publisher-id")
    private Publisher publisher;


}
