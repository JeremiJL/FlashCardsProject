package jeremijl.flashcardsproject;

public class NoEntryFound extends Exception{

    @Override
    public String getMessage() {
        return "There is no entry with such id!";
    }
}
