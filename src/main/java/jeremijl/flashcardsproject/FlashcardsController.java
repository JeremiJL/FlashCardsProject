package jeremijl.flashcardsproject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;

@Controller
public class FlashcardsController {

    FileService fileService;
    EntryRepository entryRepository;

    @Autowired
    public FlashcardsController(FileService fileService, EntryRepository entryRepository) {
        this.fileService = fileService;
        this.entryRepository = entryRepository;
    }
}
