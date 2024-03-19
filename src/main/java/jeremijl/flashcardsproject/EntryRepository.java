package jeremijl.flashcardsproject;

import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Repository
public class EntryRepository {

    private final EntityManager entityManager;
    private static  HashMap<Integer,String> sortQueryDictionary = new HashMap<>();
    static {
        sortQueryDictionary.put(Lang.ENGLISH.ordinal(), "SELECT e FROM Entry e ORDER BY e.english");
        sortQueryDictionary.put(Lang.GERMAN.ordinal(), "SELECT e FROM Entry e ORDER BY e.german");
        sortQueryDictionary.put(Lang.POLISH.ordinal(), "SELECT e FROM Entry e ORDER BY e.polish");
    }

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
    public void modifyEntry(long id, Entry entryModified) throws NoEntryFound {
        Entry entryToMod = searchForEntry(id);
        entryToMod.setEnglish(entryModified.getEnglish());
        entryToMod.setGerman(entryModified.getGerman());
        entryToMod.setPolish(entryModified.getPolish());
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

    public List<Entry> sortWithRespectToLanguage(int language, Boolean ascending) {

        //form an appropriate query
        String queryText = sortQueryDictionary.get(language);
        if (ascending)
            queryText += " asc";
        else
            queryText += " desc";
        //fetch the data from db using entity manager
        List<Entry> entries = entityManager.createQuery(queryText).getResultList();
        return entries;

    }

    public void shutDown(){
        entityManager.close();
    }
}
