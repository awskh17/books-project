package firsProject.booksProject.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    @ManyToMany(mappedBy = "authors")
            //@JoinTable(name = "Book_Author",joinColumns = @JoinColumn(name = "Author_Id"),inverseJoinColumns = @JoinColumn(name = "Book_Id"))
    private Set<Book> books=new HashSet<>();
}
