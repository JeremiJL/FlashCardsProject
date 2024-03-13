package jeremijl.flashcardsproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
@ConfigurationPropertiesScan
public class FlashCardsProjectApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext applicationContext = SpringApplication.run(FlashCardsProjectApplication.class, args);

		FileService fileService = applicationContext.getBean(FileService.class);
		EntryRepository entryRepository = applicationContext.getBean(EntryRepository.class);
		FlashcardsController flashcardsController = applicationContext.getBean(FlashcardsController.class);


	}



}
