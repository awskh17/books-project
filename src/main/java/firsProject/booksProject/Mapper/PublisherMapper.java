package firsProject.booksProject.Mapper;

import firsProject.booksProject.Dtos.PublisherDto;
import firsProject.booksProject.Entity.Publisher;

public class PublisherMapper {
    public static PublisherDto maptoPublisherDto(Publisher publisher) {
        return new PublisherDto(publisher.getId(), publisher.getName(), publisher.getBooks());
    }
    public static Publisher maptoPublisher(PublisherDto publisherDto) {
        return new Publisher(publisherDto.getId(), publisherDto.getName(),publisherDto.getBooks());
    }
}
