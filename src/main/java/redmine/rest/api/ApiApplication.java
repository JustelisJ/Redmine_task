package redmine.rest.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import redmine.rest.api.model.jira.JiraPackage;
import redmine.rest.api.service.timeentry.TimeEntryService;

import java.io.IOException;
import java.io.InputStream;

@SpringBootApplication
@Log4j2
@EnableScheduling
public class ApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class, args);
    }

    @Bean
    CommandLineRunner runner(TimeEntryService entryService, @Value("${data.url}") String dataUrl) {
        return args -> {
            // read json and post on service
            ObjectMapper mapper = new ObjectMapper();
            TypeReference<JiraPackage> typeReference = new TypeReference<>() {
            };
            InputStream inputStream = TypeReference.class.getResourceAsStream(dataUrl);
            try {
                JiraPackage jiraPackage = mapper.readValue(inputStream, typeReference);
                entryService.postJiraWorkLogs(jiraPackage);
                log.info("Work logs Saved!");
            } catch (IOException e) {
                log.error("Unable to save work logs: " + e.getMessage());
            }
        };
    }

}
