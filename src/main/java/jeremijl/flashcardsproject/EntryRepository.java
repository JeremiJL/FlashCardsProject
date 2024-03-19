package jeremijl.flashcardsproject;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface EntryRepository extends CrudRepository<Entry,Long> {

    //Sort queries
    List<Entry> findAllByIdTranslationNotNullOrderByEnglishAsc();
    List<Entry> findAllByIdTranslationNotNullOrderByEnglishDesc();

    List<Entry> findAllByIdTranslationNotNullOrderByGermanAsc();
    List<Entry> findAllByIdTranslationNotNullOrderByGermanDesc();

    List<Entry> findAllByIdTranslationNotNullOrderByPolishAsc();
    List<Entry> findAllByIdTranslationNotNullOrderByPolishDesc();

    //Helper queries
    int countALlBy();

    @Query(value = "SELECT MAX(e.idTranslation) FROM Entry e")
    Long findMaxId();

    //Necessary functionalities
    default Entry getRandomEntry() throws RuntimeException{
        //get random index
        long randomIndex = (long) (Math.random() * countALlBy());
        //if there are no entries raise an exception
        if (countALlBy() == 0)
            throw new IndexOutOfBoundsException();
        //else iterate over the entries and stop at random entry
        int counter = 0;
        for (Entry e : this.findAll()){
            if (counter++ == randomIndex)
                return e;
        }
        throw new RuntimeException();
    }

    default Long getNewId(){
        return findMaxId() + 1;
    }
}