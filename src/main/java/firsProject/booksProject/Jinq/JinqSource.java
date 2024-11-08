package firsProject.booksProject.Jinq;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceUnit;
import org.jinq.jpa.JPAJinqStream;
import org.jinq.jpa.JinqJPAStreamProvider;
import org.springframework.stereotype.Component;

@Component
public class JinqSource {
    private JinqJPAStreamProvider streams;

    @PersistenceUnit
    public void setEntityManagerFactory(
            EntityManagerFactory emf) throws Exception{
        streams =new JinqJPAStreamProvider(emf);
    }
    public <U>JPAJinqStream<U> streamAll(EntityManager em , Class<U> entity){return streams.streamAll(em, entity);}

}
