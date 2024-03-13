package jeremijl.flashcardsproject;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EntryRepository {

    private List<Entry> entryList;

    public EntryRepository() {
        //entryList =
    }

    public void addEntry(Entry entry){
        entryList.add(entry);
    }

}
