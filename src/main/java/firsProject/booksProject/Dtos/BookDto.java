package firsProject.booksProject.Dtos;

import firsProject.booksProject.Entity.Author;
import firsProject.booksProject.Entity.Publisher;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Flow;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookDto implements Serializable {
    private long id;
    private String title;
    private String type;
    private String summary;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateOfPublish;
    private int numOfPublish;
    private Set<Author> authors=new HashSet<>();
    private Publisher publisher;

    public Set<String> getAuthorsName(){
        Set<String> res=new HashSet<>();
        for(Author author:authors) res.add(author.getName());
        return res;
    }

}
