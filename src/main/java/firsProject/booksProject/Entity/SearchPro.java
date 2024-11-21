package firsProject.booksProject.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SearchPro {
    String title;
    String type;
    String summary;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Date dateOfPublish;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Date dateOfPublish1;
    String publisher;
}
