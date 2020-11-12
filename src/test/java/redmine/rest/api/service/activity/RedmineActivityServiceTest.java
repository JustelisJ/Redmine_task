package redmine.rest.api.service.activity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class RedmineActivityServiceTest {

    private static final String ACTIVITY_DOCUMENTATION_NAME = "Documentation";
    private static final String SOME_OTHER_ACTIVITY_NAME = "asdasd";

    @Mock
    ActivityService activityService;
    Long documentationId;
    Long default_id;

    private void initializeMock() {
        MockitoAnnotations.initMocks(this);
    }

    private void initializeData() {
        documentationId = 4L;
        default_id = 5L;
    }

    @BeforeEach
    void setUp() {
        initializeMock();
        initializeData();
    }

    @Test
    void findActivityFromName() {
        when(activityService.findActivityFromName(any())).thenReturn(default_id);
        when(activityService.findActivityFromName(ACTIVITY_DOCUMENTATION_NAME)).thenReturn(documentationId);

        Long id = activityService.findActivityFromName(ACTIVITY_DOCUMENTATION_NAME);
        assertEquals(documentationId, id);
    }

    @Test
    void dontFindActivityFromName() {
        when(activityService.findActivityFromName(any())).thenReturn(default_id);
        when(activityService.findActivityFromName(ACTIVITY_DOCUMENTATION_NAME)).thenReturn(documentationId);

        Long id = activityService.findActivityFromName(SOME_OTHER_ACTIVITY_NAME);
        assertEquals(default_id, id);
    }
}