package jeremijl.flashcardsproject.WordPrinters;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile("lower")
@Service
public class LowerPrinter implements WordPrinter{

    @Override
    public void printText(String text) {
        System.out.print(text.toLowerCase());
    }
}
