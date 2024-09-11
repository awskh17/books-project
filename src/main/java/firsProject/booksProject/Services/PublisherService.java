package firsProject.booksProject.Services;

import firsProject.booksProject.Dtos.PublisherDto;
import firsProject.booksProject.Entity.Publisher;

public interface PublisherService {
    PublisherDto addPublisher(PublisherDto publisherDto);
    PublisherDto findPublisherById(long id);
    PublisherDto findPublisherByName(String name);

}
