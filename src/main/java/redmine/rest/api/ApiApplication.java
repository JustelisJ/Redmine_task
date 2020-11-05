package redmine.rest.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class, args);
    }

//    @Bean
//    CommandLineRunner runner(TimeEntryService entryService, @Value("${data_url}") String data_url) {
//        return args -> {
//            // read json and post on service
//            ObjectMapper mapper = new ObjectMapper();
//            TypeReference<JiraPackage> typeReference = new TypeReference<JiraPackage>() {
//            };
//            InputStream inputStream = TypeReference.class.getResourceAsStream(data_url);
//            try {
//                JiraPackage jiraPackage = mapper.readValue(inputStream, typeReference);
//                for (JiraResult result : jiraPackage.getResults()) {
//                    entryService.postTimeEntry(result);
//                }
//                System.out.println("Users Saved!");
//            } catch (IOException e) {
//                System.out.println("Unable to save users: " + e.getMessage());
//            }
//        };
//    }

}
