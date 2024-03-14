package jeremijl.flashcardsproject;

import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Random;
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

    public Entry getRandomEntry(){

        //some way of providing 'random' value of set
        int randomIndex = (int) (Math.random() * entrySet.size());
        int i = 0;
        for (Entry e : entrySet){
            if (i++ == randomIndex)
                return e;
        }
        //else something bad happended
        return null;
    }
}
