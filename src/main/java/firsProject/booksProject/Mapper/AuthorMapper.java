package firsProject.booksProject.Mapper;

import firsProject.booksProject.Dtos.AuthorDto;
import firsProject.booksProject.Entity.Author;

public class AuthorMapper {
    public static Author maptoAuthor(AuthorDto authorDto) {
        return new Author(authorDto.getId(), authorDto.getName(),authorDto.getBooks());
    }
    public static AuthorDto maptoAuthorDto(Author author) {
        return new AuthorDto(author.getId(), author.getName(), author.getBooks());
    }
}
