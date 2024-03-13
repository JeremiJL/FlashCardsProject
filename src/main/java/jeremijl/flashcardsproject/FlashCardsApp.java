package jeremijl.flashcardsproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@ConfigurationPropertiesScan
public class FlashCardsApp {

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(FlashCardsApp.class, args);

        FileServiceCSV fileService = applicationContext.getBean(FileServiceCSV.class);
        EntryRepository entryRepository = applicationContext.getBean(EntryRepository.class);
        FlashcardsController flashcardsController = applicationContext.getBean(FlashcardsController.class);


    }



}