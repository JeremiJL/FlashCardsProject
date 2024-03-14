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

        //get random entry
        Entry randomEntry = entryRepository.getRandomEntry();
        //select language which translation will be missing
        Lang selectedLang = Lang.selectRandomLanguage();
        Lang[] filteredArr = (Lang[]) Arrays.stream(Lang.values()).filter(e -> !e.equals(selectedLang)).toArray(Lang[]::new);
        //prepare massage
        System.out.println("Provide missing translation");
        wordPrinter.printText(randomEntry.selectedLangFormat(filteredArr) + "\n");
        //collect user input
        String input = consoleScanner.nextLine().toLowerCase();
        // compare and print result
        String correctAnswer = randomEntry.translationMap.get(selectedLang);
        if (input.equals(correctAnswer.toLowerCase())) {
            System.out.println("You are correct!");
        }
        else {
            System.out.print("Incorrect! Correct answer is : ");
            wordPrinter.printText(correctAnswer + "\n");
        }
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


}
