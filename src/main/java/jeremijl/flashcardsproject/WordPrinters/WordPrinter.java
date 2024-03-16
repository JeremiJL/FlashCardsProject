package jeremijl.flashcardsproject.WordPrinters;

public interface WordPrinter {

    void printText(String text);

    default void printLine(String text){
        printText(text + "\n");
    }

}
