package firsProject.booksProject.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;

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
    @FullTextField
    private String name;
    @ManyToMany(mappedBy = "authors")
    private Set<Book> books=new HashSet<>();

}
