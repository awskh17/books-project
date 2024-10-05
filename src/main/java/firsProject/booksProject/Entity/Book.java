package firsProject.booksProject.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

//@Indexed
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
//    @FullTextField
    private String title;
//    @FullTextField
    private String type;
//    @FullTextField
    private String summary;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateOfPublish;
//    @GenericField
    private int numOfPublish;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "Book_Author",joinColumns = @JoinColumn(name = "Book_Id"),inverseJoinColumns = @JoinColumn(name = "Author_Id"))
//    @IndexedEmbedded
    private Set<Author> authors=new HashSet<>();
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "publisher_Id")
    private Publisher publisher;

}
