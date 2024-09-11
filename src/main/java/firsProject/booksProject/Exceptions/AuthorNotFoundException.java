package firsProject.booksProject.Exceptions;

public class AuthorNotFoundException extends RuntimeException {
  public AuthorNotFoundException(String message) {
    super(message);
  }
}
