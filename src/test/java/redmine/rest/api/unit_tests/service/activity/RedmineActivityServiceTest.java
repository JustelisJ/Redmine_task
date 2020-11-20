package redmine.rest.api.unit_tests.service.activity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;
import redmine.rest.api.model.Activity;
import redmine.rest.api.model.redminedata.ActivityData;
import redmine.rest.api.service.activity.ActivityService;
import redmine.rest.api.service.activity.RedmineActivityService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

class RedmineActivityServiceTest {

    private static final long DOCUMENTATION_ID = 4L;
    private static final String ACTIVITY_DOCUMENTATION_NAME = "Documentation";
    private static final String SOME_OTHER_ACTIVITY_NAME = "asdasd";
    private static final long DEFAULT_ID = 5L;

    @Mock
    RestTemplate restTemplate;

    ActivityService activityService;

    ActivityData activityData;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        activityService = new RedmineActivityService(restTemplate);

        initializeActivityData();
    }

    private void initializeActivityData() {
        activityData = new ActivityData();
        List<Activity> activities = new ArrayList<>();
        Activity documentationActivity = new Activity();
        Activity asdasdActivity = new Activity();

        documentationActivity.setActive(true);
        documentationActivity.setIsDefault(false);
        documentationActivity.setId(DOCUMENTATION_ID);
        documentationActivity.setName(ACTIVITY_DOCUMENTATION_NAME);

        asdasdActivity.setActive(true);
        asdasdActivity.setIsDefault(true);
        asdasdActivity.setId(DEFAULT_ID);
        asdasdActivity.setName(SOME_OTHER_ACTIVITY_NAME);
    }

    @Test
    void findActivityFromName() {
        when(restTemplate.getForObject(anyString(), eq(ActivityData.class))).thenReturn(activityData);
        Long id = activityService.findActivityFromName(ACTIVITY_DOCUMENTATION_NAME);
        assertEquals(DOCUMENTATION_ID, id);
    }

    @Test
    void dontFindActivityFromName() {
        when(restTemplate.getForObject(anyString(), eq(ActivityData.class))).thenReturn(activityData);
        Long id = activityService.findActivityFromName(SOME_OTHER_ACTIVITY_NAME);
        assertEquals(DEFAULT_ID, id);
    }
}