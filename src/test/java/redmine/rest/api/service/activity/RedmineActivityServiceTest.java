package redmine.rest.api.service.activity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
        when(activityService.findActivityFromName(any())).thenReturn(Optional.of(default_id));
        when(activityService.findActivityFromName(ACTIVITY_DOCUMENTATION_NAME)).thenReturn(Optional.of(documentationId));

        Optional<Long> id = activityService.findActivityFromName(ACTIVITY_DOCUMENTATION_NAME);
        assertTrue(id.isPresent());
        assertEquals(documentationId, id.get());
    }

    @Test
    void dontFindActivityFromName() {
        when(activityService.findActivityFromName(any())).thenReturn(Optional.of(default_id));
        when(activityService.findActivityFromName(ACTIVITY_DOCUMENTATION_NAME)).thenReturn(Optional.of(documentationId));

        Optional<Long> id = activityService.findActivityFromName(SOME_OTHER_ACTIVITY_NAME);
        assertTrue(id.isPresent());
        assertEquals(default_id, id.get());
    }
}