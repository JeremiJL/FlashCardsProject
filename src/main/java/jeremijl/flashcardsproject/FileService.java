package jeremijl.flashcardsproject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;


@Service
@PropertySource("classpath:new.properties")
public class FileService {

    public String filePath;

    public FileService(@Value("${external.values.filepath}") String filePath) {
        this.filePath = filePath;
    }
}
