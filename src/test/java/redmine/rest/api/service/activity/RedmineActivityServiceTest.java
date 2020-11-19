package redmine.rest.api.service.activity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.web.client.RestTemplate;
import redmine.rest.api.config.RestTemplateConfig;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Import(RestTemplateConfig.class)
class RedmineActivityServiceTest {

    private static final String ACTIVITY_DOCUMENTATION_NAME = "Documentation";
    private static final String SOME_OTHER_ACTIVITY_NAME = "asdasd";

    @Autowired
    RestTemplate restTemplate;

    ActivityService activityService;
    Long documentationId;
    Long default_id;

    private void initializeData() {
        documentationId = 4L;
        default_id = 5L;
    }

    @BeforeEach
    void setUp() {
        activityService = new RedmineActivityService(restTemplate);

        initializeData();
    }

    @Test
    void findActivityFromName() {
        Long id = activityService.findActivityFromName(ACTIVITY_DOCUMENTATION_NAME);
        assertEquals(documentationId, id);
    }

    @Test
    void dontFindActivityFromName() {
        Long id = activityService.findActivityFromName(SOME_OTHER_ACTIVITY_NAME);
        assertEquals(default_id, id);
    }
}