package firsProject.booksProject;

import firsProject.booksProject.Entity.Book;
import jakarta.persistence.EntityManager;
import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class EventListener implements ApplicationListener<ContextRefreshedEvent> {
    private final EntityManager em;
    private final SearchSession search;

    public EventListener(EntityManager em)  {
        this.em = em;
        search= Search.session(em);
    }



    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        try {
            search.massIndexer(Book.class).batchSizeToLoadObjects(10).idFetchSize(10).threadsToLoadObjects(1).startAndWait();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean supportsAsyncExecution() {
        return ApplicationListener.super.supportsAsyncExecution();
    }
}
