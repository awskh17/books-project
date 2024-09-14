package firsProject.booksProject.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class NumOfPublishException extends RuntimeException {
    public NumOfPublishException(String message) {
        super(message);
    }
}
