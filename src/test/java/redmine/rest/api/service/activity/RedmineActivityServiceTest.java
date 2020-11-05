package redmine.rest.api.service.activity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class RedmineActivityServiceTest {

    @Mock
    ActivityService activityService;
    Optional<Long> id;
    Optional<Long> default_id;

    @BeforeEach
    void setUp() {
        id = Optional.of(4L);
        default_id = Optional.of(5L);
    }

    @Test
    void findActivityFromName() {
        when(activityService.findActivityFromName(any())).thenReturn(default_id);
        when(activityService.findActivityFromName("Documentation")).thenReturn(id);

        Optional<Long> id = activityService.findActivityFromName("Documentation");
        assertTrue(id.isPresent());
        assertEquals(4L, id.get());
    }

    @Test
    void dontFindActivityFromName() {
        when(activityService.findActivityFromName(any())).thenReturn(default_id);
        when(activityService.findActivityFromName("Documentation")).thenReturn(id);

        Optional<Long> id = activityService.findActivityFromName("asdasd");
        assertTrue(id.isPresent());
        assertEquals(5L, id.get());
    }
}