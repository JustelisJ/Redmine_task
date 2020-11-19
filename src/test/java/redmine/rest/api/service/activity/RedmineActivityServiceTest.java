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

    private static final long DOCUMENTATION_ID = 4L;
    private static final String ACTIVITY_DOCUMENTATION_NAME = "Documentation";
    private static final String SOME_OTHER_ACTIVITY_NAME = "asdasd";
    private static final long DEFAULT_ID = 5L;

    @Autowired
    RestTemplate restTemplate;

    ActivityService activityService;


    @BeforeEach
    void setUp() {
        activityService = new RedmineActivityService(restTemplate);
    }

    @Test
    void findActivityFromName() {
        Long id = activityService.findActivityFromName(ACTIVITY_DOCUMENTATION_NAME);
        assertEquals(DOCUMENTATION_ID, id);
    }

    @Test
    void dontFindActivityFromName() {
        Long id = activityService.findActivityFromName(SOME_OTHER_ACTIVITY_NAME);
        assertEquals(DEFAULT_ID, id);
    }
}