package jeremijl.flashcardsproject;

import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Set;

@Repository
public class EntryRepository {

    private final Set<Entry> entrySet;

    public EntryRepository() {
        entrySet = new HashSet<>();
    }

    public void addEntry(Entry entry){
        entrySet.add(entry);
    }

    public Set<Entry> getEntrySet() {
        return entrySet;
    }
}
