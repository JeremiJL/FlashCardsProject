package jeremijl.flashcardsproject;

import jeremijl.flashcardsproject.WordPrinters.WordPrinter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.*;

@Controller
public class FlashcardsController extends Thread{

    //Beans
    FileServiceCSV fileService;
    EntryRepository entryRepository;
    WordPrinter wordPrinter;

    //User interface running condition
    private boolean running;

    //Console scanner
    Scanner consoleScanner = new Scanner(System.in);


    @Autowired
    public FlashcardsController(FileServiceCSV fileService, EntryRepository entryRepository, WordPrinter wordPrinter) {
        //Assigning
        this.fileService = fileService;
        this.entryRepository = entryRepository;
        this.wordPrinter = wordPrinter;

        //Init
        fileService.extractEntriesFromFile();
        running = true;
        this.start();
    }

    @Override
    public void run() {


        //User console - interface loop
        String input = "";

        while(running){

            System.out.println("\nWhat would you like to do?\n" +
                    "a) add a new word to the dictionary\n" +
                    "b) display all words from the dictionary\n" +
                    "c) test your language skills\n" +
                    "d) quit\n" +
                    "Your option : ");

            input = consoleScanner.nextLine();

            switch (input) {
                case "a" -> addNewWord();
                case "b" -> displayAllWords();
                case "c" -> testLanguageSkills();
                case "d" -> running = false;
            }

        }

    }

    private void testLanguageSkills(){



    }

    private void addNewWord(){

        //prompt
        System.out.println("Add new dictionary entry.");
        Map<Lang,String> translations = new HashMap<>();
        String value = "";

        //ask for consecutive translations
        for (Lang lang : Lang.values()){
            System.out.print("Provide " + lang.toString() + " translation : ");
            value = consoleScanner.nextLine();
            translations.put(lang,value);
        }

        //tell writer to update dictionary
        Entry newEntry = new Entry(translations);
        fileService.writeEntriesToFile(newEntry);

    }

    private void displayAllWords(){

        for (Entry entry : entryRepository.getEntrySet()){
            wordPrinter.printText(entry.toString() + "\n");
        }

    }

    private void test(){

//        Map<Lang, String> translations1 = new HashMap<>();
//        translations1.put(Lang.ENGLISH, "flower");
//        translations1.put(Lang.GERMAN, "Blume");
//        translations1.put(Lang.POLISH, "kwiat");
//
//        Entry entry1 = new Entry(translations1);
//
//        fileService.writeEntriesToFile(entry1);

        System.out.println(Arrays.toString(Lang.values()));
    }



}
