package firsProject.booksProject.Services.ServiceImpl;

import firsProject.booksProject.Dtos.PublisherDto;
import firsProject.booksProject.Entity.Publisher;
import firsProject.booksProject.Exceptions.PublisherNotFoundException;
import firsProject.booksProject.Mapper.PublisherMapper;
import firsProject.booksProject.Repositories.PublisherRepo;
import firsProject.booksProject.Services.PublisherService;
import org.springframework.stereotype.Service;

@Service
public class PublisherServiceImpl implements PublisherService {
    PublisherRepo publisherRepo;

    public PublisherServiceImpl(PublisherRepo publisherRepo) {
        this.publisherRepo = publisherRepo;
    }

    @Override
    public PublisherDto addPublisher(PublisherDto publisherDto) {
        Publisher savedPublisher=publisherRepo.save(PublisherMapper.maptoPublisher(publisherDto));
        return PublisherMapper.maptoPublisherDto(savedPublisher);
    }

    @Override
    public PublisherDto findPublisherById(long id) {
        Publisher publisher=publisherRepo.findByid(id);
        if(publisher==null) throw new PublisherNotFoundException("Publisher with id : "+id+" not found");
        return PublisherMapper.maptoPublisherDto(publisher);
    }

    @Override
    public PublisherDto findPublisherByName(String name) {
        Publisher publisher=publisherRepo.findByName(name);
        if(publisher==null) throw new PublisherNotFoundException("Publisher with name : "+name+" not found");
        return PublisherMapper.maptoPublisherDto(publisher);
    }


}
