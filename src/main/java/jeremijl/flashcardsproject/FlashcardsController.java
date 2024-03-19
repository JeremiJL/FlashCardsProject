package jeremijl.flashcardsproject;

import jeremijl.flashcardsproject.WordPrinters.WordPrinter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.pulsar.PulsarProperties;
import org.springframework.stereotype.Controller;

import java.lang.reflect.Method;
import java.util.*;
import java.util.function.Function;

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
                    "f) sort and display words\n" +
                    "g) test your language skills\n" +
                    "h) quit\n" +
                    "Your option : ");

            input = consoleScanner.nextLine();

            switch (input) {
                case "a" -> addNewEntry();
                case "b" -> searchForWord();
                case "c" -> modifyEntry();
                case "d" -> deleteEntry();
                case "e" -> displayAllWords();
                case "f" -> sortAndDisplay();
                case "g" -> testLanguageSkills();
                case "h" -> shutDown();
            }

        }
    }

    private void sortAndDisplay(){

        try {
            //prompt, ask about language
            System.out.println("With respect to which language do you want to sort dictionary records?");
            System.out.println("a) " + Lang.ENGLISH + "\nb) " + Lang.GERMAN + "\nc) " + Lang.POLISH);
            String inputLang = consoleScanner.nextLine().toLowerCase();

            // prompt, ask about order
            System.out.println("Should records be sorted in ascending order or descending order?\n[asc / desc] ?");
            Boolean inputAsc = consoleScanner.nextLine().equalsIgnoreCase("asc");

            //choose corresponding method
            List<Entry> sortedEntries;
            switch (inputLang) {
                case "a":
                    if (inputAsc)
                        sortedEntries = entryRepository.findAllByIdTranslationNotNullOrderByEnglishAsc();
                    else
                        sortedEntries = entryRepository.findAllByIdTranslationNotNullOrderByEnglishDesc();
                    break;
                case "b":
                    if (inputAsc)
                        sortedEntries = entryRepository.findAllByIdTranslationNotNullOrderByGermanAsc();
                    else
                        sortedEntries = entryRepository.findAllByIdTranslationNotNullOrderByGermanDesc();
                    break;
                case "c":
                    if (inputAsc)
                        sortedEntries = entryRepository.findAllByIdTranslationNotNullOrderByPolishAsc();
                    else
                        sortedEntries = entryRepository.findAllByIdTranslationNotNullOrderByPolishDesc();
                    break;
                default:
                    throw new InputMismatchException();
            }

            //Print results
            System.out.println("Sorted results :");
            for (Entry e : sortedEntries){
                System.out.println(e.toString());
            }

        } catch(InputMismatchException inm){
            System.out.println("Wrong input format.");
        }

    }

    private void shutDown(){
        running = false;
    }

    private void deleteEntry() {
        try{
            //prompt
            System.out.println("Please, provide entry id : ");
            //demand id from user
            long input = Long.parseLong(consoleScanner.nextLine());
            //display entry that is about to be deleted
            System.out.println("Entry to be deleted : " + entryRepository.findById(input).orElseThrow());
            //delegate removal to entryRepository
            entryRepository.deleteById(input);

        } catch (ClassCastException cce){
            System.out.println("Wrong id format. Entry id is an integer.");
        } catch (NoSuchElementException nsee){
            System.out.println("There is no entry with corresponding id.");
        }
    }

    private void modifyEntry() {
        try{
            //ask for id
            System.out.println("Please, provide entry id : ");
            long inputId = Long.parseLong(consoleScanner.nextLine());
            //search for entry that user is willing to modify
            Entry entryToMod = entryRepository.findById(inputId).orElseThrow();
            //aks for new English translation
            System.out.print("Provide new translation for ");
            wordPrinter.printLine(entryToMod.getEnglish() + " : ");
            String newEnglish = consoleScanner.nextLine();
            //aks for new German translation
            System.out.print("Provide new translation for ");
            wordPrinter.printLine(entryToMod.getGerman() + " : ");
            String newGerman = consoleScanner.nextLine();
            //aks for new Polish translation
            System.out.print("Provide new translation for ");
            wordPrinter.printLine(entryToMod.getPolish() + " : ");
            String newPolish = consoleScanner.nextLine();

            //make changes
            entryToMod.setEnglish(newEnglish);
            entryToMod.setGerman(newGerman);
            entryToMod.setPolish(newPolish);
            //save this entry into db
            entryRepository.save(entryToMod);

        } catch (NoSuchElementException e) {
            System.out.println("There is no entry with corresponding id.");
        }
    }

    private void searchForWord() {
        try{
            //ask for id
            System.out.println("Please, provide entry id : ");
            long input = Long.parseLong(consoleScanner.nextLine());
            //fetch entry with given id
            Optional<Entry> result = entryRepository.findById(input);
            //display
            wordPrinter.printLine(result.orElseThrow().toString());

        } catch (ClassCastException cce){
            System.out.println("Wrong id format. Entry id is an integer.");
        } catch (NoSuchElementException e) {
            System.out.println("There is no entry with corresponding id.");
        }
    }

    private void testLanguageSkills(){
        try {

            //get random entry
            Entry randomEntry = entryRepository.getRandomEntry();

            //get random language index
            int missingLang = (int) (Math.random() * Lang.values().length);

            //prepare message
            String[] wordsArray = randomEntry.toArray();
            String[] forDisplay = Arrays.copyOf(wordsArray, wordsArray.length);
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
            } else {
                System.out.print("Incorrect! Correct answer is : ");
                wordPrinter.printLine(correctAnswer);
            }
        }catch (IndexOutOfBoundsException ioobe){
            System.out.println("No entries in dictionary.");
        } catch (RuntimeException re){
            re.printStackTrace();
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
        entryRepository.save(newEntry);
    }

    private void displayAllWords(){
        for (Entry entry : entryRepository.findAll()){
            wordPrinter.printLine(entry.toString());
        }
    }


}
