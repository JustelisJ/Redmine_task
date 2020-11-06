package redmine.rest.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

@Configuration
public class FileConfig {

    @Bean
    public File incorrectJSONFile() {
        return new File(getResourcesAbsolutePath());
    }

    private String getResourcesAbsolutePath() {
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date());
        File currentDirFile = new File("");
        String helper = currentDirFile.getAbsolutePath();
        String currentDir = helper + "/src/main/resources/json/incorrect_" + timeStamp + ".json";
        return currentDir;
    }


}
