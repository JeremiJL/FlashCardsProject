package jeremijl.flashcardsproject.WordPrinters;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile("original")
@Service
public class OriginalPrinter implements WordPrinter {

    @Override
    public void printText(String text) {
        System.out.print(text);
    }
}
