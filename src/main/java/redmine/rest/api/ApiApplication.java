package redmine.rest.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import redmine.rest.api.model.jira.JiraPackage;
import redmine.rest.api.model.jira.JiraResult;
import redmine.rest.api.service.timeEntry.TimeEntryService;

import java.io.IOException;
import java.io.InputStream;

@SpringBootApplication
public class ApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class, args);
    }

    @Bean
    CommandLineRunner runner(TimeEntryService entryService, @Value("${data_url}") String data_url) {
        return args -> {
            // read json and post on service
            ObjectMapper mapper = new ObjectMapper();
            TypeReference<JiraPackage> typeReference = new TypeReference<JiraPackage>() {
            };
            InputStream inputStream = TypeReference.class.getResourceAsStream(data_url);
            try {
                JiraPackage jiraPackage = mapper.readValue(inputStream, typeReference);
                for (JiraResult result : jiraPackage.getResults()) {
                    entryService.postTimeEntry(result);
                }
                System.out.println("Users Saved!");
            } catch (IOException e) {
                System.out.println("Unable to save users: " + e.getMessage());
            }
        };
    }

}
