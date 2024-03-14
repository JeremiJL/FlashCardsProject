package jeremijl.flashcardsproject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


@Service
@PropertySource("classpath:new.yaml")
public class FileServiceCSV implements FileService {

    //path of file containing data
    private final String filePath;

    //separator used in data format
    private final String separator = ";";

    //repository that stores entries
    private final EntryRepository entryRepository;


    public FileServiceCSV(@Value("${pl.edu.pja.tpo02.filename}") String filePath, EntryRepository entryRepository) {
        this.filePath = filePath;
        this.entryRepository = entryRepository;
    }

    public void extractEntriesFromFile(){

        try {

            //Open file containing raw entry data in csv format
            File file = new File(filePath);
            Scanner scanner = new Scanner(file);

            while (scanner.hasNext()){
                //Reading first line of file
                String line = scanner.nextLine();
                String[] values = line.split(separator);
                //Putting words into map
                Map<Lang, String> translations = new HashMap<>();
                translations.put(Lang.ENGLISH, values[0]);
                translations.put(Lang.GERMAN, values[1]);
                translations.put(Lang.POLISH, values[2]);
                //Adding entry object into repository
                entryRepository.addEntry(new Entry(translations));
            }

            scanner.close();
        } catch (IOException ioe){
            ioe.printStackTrace();
        }

    }

    @Override
    public void writeEntriesToFile(Entry entry) {

        //check if entry is already present in entry repository
        if (!entryRepository.getEntrySet().contains(entry)){
            try {
                //Open file containing raw entry data in csv format
                File file = new File(filePath);
                FileWriter fileWriter = new FileWriter(file , true);

                fileWriter.write(entry.toCSV());
                fileWriter.write("\n");

                fileWriter.close();
            } catch (IOException ioe){
                ioe.printStackTrace();
            }
        }

        //after writing to file - update dictionary
        extractEntriesFromFile();
    }


}
