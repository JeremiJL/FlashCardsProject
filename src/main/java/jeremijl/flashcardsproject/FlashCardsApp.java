package jeremijl.flashcardsproject;

import jeremijl.flashcardsproject.WordPrinters.WordPrinter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@ConfigurationPropertiesScan
public class FlashCardsApp {

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(FlashCardsApp.class, args);

        applicationContext.getBean(FileService.class);
        applicationContext.getBean(EntryRepository.class);
        applicationContext.getBean(FlashcardsController.class);
        applicationContext.getBean(WordPrinter.class);


    }



}