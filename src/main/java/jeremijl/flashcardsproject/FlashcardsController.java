package jeremijl.flashcardsproject;

import jeremijl.flashcardsproject.WordPrinters.WordPrinter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.*;

@Controller
public class FlashcardsController extends Thread{

    //Beans
    EntryRepository entryRepository;
    WordPrinter wordPrinter;

    //User interface running condition
    private boolean running;

    //Console scanner
    Scanner consoleScanner = new Scanner(System.in);


    @Autowired
    public FlashcardsController(EntryRepository entryRepository, WordPrinter wordPrinter) {
        //Assigning
        this.entryRepository = entryRepository;
        this.wordPrinter = wordPrinter;
        //Init
        running = true;
        this.start();
    }

    @Override
    public void run() {
        //User console - interface loop
        String input;

        while(running){

            System.out.println("\nWhat would you like to do?\n" +
                    "a) add a new entry to the dictionary\n" +
                    "b) search for a word in the dictionary\n" +
                    "c) modify entries in the dictionary\n" +
                    "d) delete entries in the dictionary\n" +
                    "e) display all words from the dictionary\n" +
                    "f) test your language skills\n" +
                    "g) quit\n" +
                    "Your option : ");

            input = consoleScanner.nextLine();

            switch (input) {
                case "a" -> addNewEntry();
                case "b" -> searchForWord();
                case "c" -> modifyEntry();
                case "d" -> deleteEntry();
                case "e" -> displayAllWords();
                case "f" -> testLanguageSkills();
                case "g" -> shutDown();
            }

        }
    }

    private void shutDown(){
        running = false;
        entryRepository.shutDown();
    }

    private void deleteEntry() {
        try{
            //prompt
            System.out.println("Please, provide entry id : ");
            //demand id from user
            long input = Long.parseLong(consoleScanner.nextLine());
            //delegate removal to entryRepository
            entryRepository.removeEntry(input);

        } catch (ClassCastException cce){
            System.out.println("Wrong id format. Entry id is an integer.");
        } catch (NoEntryFound e) {
            System.out.println(e.getMessage());
        }
    }

    private void modifyEntry() {
        try{
            //ask for id
            System.out.println("Please, provide entry id : ");
            long inputId = Long.parseLong(consoleScanner.nextLine());
            //search for entry that user is willing to modify
            Entry entry = entryRepository.searchForEntry(inputId);
            //aks for new English translation
            System.out.print("Provide new translation for ");
            wordPrinter.printLine(entry.getEnglish() + " : ");
            String newEnglish = consoleScanner.nextLine();
            //aks for new German translation
            System.out.print("Provide new translation for ");
            wordPrinter.printLine(entry.getGerman() + " : ");
            String newGerman = consoleScanner.nextLine();
            //aks for new Polish translation
            System.out.print("Provide new translation for ");
            wordPrinter.printLine(entry.getPolish() + " : ");
            String newPolish = consoleScanner.nextLine();

            //Modify this entry
            entryRepository.modifyEntry(inputId, new Entry(inputId, newEnglish,newGerman,newPolish));

        } catch (NoEntryFound e) {
            System.out.println(e.getMessage());
        }
    }

    private void searchForWord() {
        try{
            //ask for id
            System.out.println("Please, provide entry id : ");
            long input = Long.parseLong(consoleScanner.nextLine());
            //fetch entry with given id
            Entry result = entryRepository.searchForEntry(input);
            //display
            wordPrinter.printLine(result.toString());

        } catch (ClassCastException cce){
            System.out.println("Wrong id format. Entry id is an integer.");
        } catch (NoEntryFound e) {
            System.out.println(e.getMessage());
        }
    }

    private void testLanguageSkills(){
        //get random entry
        Entry randomEntry = entryRepository.getRandomEntry();

        //get random language index
        int missingLang = (int) (Math.random() * Lang.values().length);

        //prepare message
        String[] wordsArray = randomEntry.toArray();
        String[] forDisplay = Arrays.copyOf(wordsArray,wordsArray.length);
        forDisplay[missingLang] = "...";

        //send message
        wordPrinter.printLine(String.join(" - ", forDisplay));
        System.out.println("Provide missing translation : ");

        //get input
        String input = consoleScanner.nextLine().toLowerCase();
        String correctAnswer = wordsArray[missingLang].toLowerCase();

        //compare
        if (input.equals(correctAnswer)) {
            System.out.println("You are correct!");
        }
        else {
            System.out.print("Incorrect! Correct answer is : ");
            wordPrinter.printLine( correctAnswer);
        }
    }

    private void addNewEntry(){

        //prompt
        System.out.println("Add new dictionary entry.");
        Map<Lang,String> translations = new HashMap<>();
        String value;

        //ask for consecutive translations
        for (Lang lang : Lang.values()){
            System.out.print("Provide " + lang.toString() + " translation : ");
            value = consoleScanner.nextLine();
            translations.put(lang,value);
        }

        //update dictionary
        Entry newEntry = new Entry(entryRepository.getNewId(),
                translations.get(Lang.ENGLISH),translations.get(Lang.GERMAN),translations.get(Lang.POLISH));
        entryRepository.addEntry(newEntry);
    }

    private void displayAllWords(){
        for (Entry entry : entryRepository.getAllEntries()){
            wordPrinter.printLine(entry.toString());
        }
    }


}
