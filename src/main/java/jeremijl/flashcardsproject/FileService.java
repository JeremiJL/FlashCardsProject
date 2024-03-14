package jeremijl.flashcardsproject;

import org.springframework.beans.factory.annotation.Value;

public interface FileService {

    void extractEntriesFromFile();

    void writeEntriesToFile(Entry entry);

}
