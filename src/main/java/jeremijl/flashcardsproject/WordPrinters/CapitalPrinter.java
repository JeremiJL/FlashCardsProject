package jeremijl.flashcardsproject.WordPrinters;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile("capital")
@Service
public class CapitalPrinter implements WordPrinter {

    @Override
    public void printText(String text) {
        System.out.print(text.toUpperCase());
    }
}
