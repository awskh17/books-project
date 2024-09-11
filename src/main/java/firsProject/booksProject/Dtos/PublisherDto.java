package firsProject.booksProject.Dtos;

import firsProject.booksProject.Entity.Book;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class PublisherDto {
    private long id;
    private String name;
    private Set<Book> books=new HashSet<>();
}
