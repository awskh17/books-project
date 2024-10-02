package firsProject.booksProject.Dtos;

import firsProject.booksProject.Entity.Publisher;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Flow;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {
    private long id;
    private String title;
    private String type;
    private String summary;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateOfPublish;
    private int numOfPublish;
    private Set<String> authors=new HashSet<>();
    private Publisher publisher;

}
