package jeremijl.flashcardsproject;

import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Repository
public class EntryRepository {

    private final EntityManager entityManager;

    public EntryRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    public void addEntry(Entry entry){
        entityManager.persist(entry);
    }

    public Entry searchForEntry(Long id) throws NoEntryFound{
        return Optional.ofNullable(entityManager.find(Entry.class, id)).orElseThrow(NoEntryFound::new);
    }

    @Transactional
    public void removeEntry(long id) throws NoEntryFound {
        entityManager.remove(searchForEntry(id));
    }

    @Transactional
    public void modifyEntry(long id){
//        Book dbbook = findById(book.getIdOfBook())
//                .orElseThrow(BookNotFoundException::new);
//        dbbook.setAuthor(book.getAuthor());
//        dbbook.setPrice(book.getPrice());
//        dbbook.setTitle(book.getTitle());
//        return dbbook;
    }

    public Entry getRandomEntry(){
        //get random index
        int randomIndex = (int) (Math.random() * getAllEntries().size());
        return getAllEntries().get(randomIndex);
    }

    public Long getNewId(){
        long lastId = getAllEntries().stream().map(Entry::getIdTranslation).max(Long::compareTo).orElse(0L);
        return lastId + 1;
    }

    public List<Entry> getAllEntries() {
        List<Entry> entries = entityManager.createQuery("SELECT e FROM Entry e").getResultList();
        return entries;
    }
}
