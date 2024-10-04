package firsProject.booksProject.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class UserFoundException extends RuntimeException {
    public UserFoundException(String message) {
        super(message);
    }
}
