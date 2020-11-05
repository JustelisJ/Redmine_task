package redmine.rest.api.service.issue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.when;

@SpringBootTest
class RedmineIssueServiceTest {

    @Mock
    IssueService issueService;
    Optional<Long> id;

    @BeforeEach
    void setUp() {
        id = Optional.of(2L);
    }

    @Test
    void getIssueIdFromName() {
        when(issueService.getIssueIdFromName("NP-1")).thenReturn(id);
        Optional<Long> id = issueService.getIssueIdFromName("NP-1");
        assertTrue(id.isPresent());
        assertEquals(2L, id.get());
    }

    @Test
    void dontGetIssueIdFromName() {
        when(issueService.getIssueIdFromName("NP-1")).thenReturn(id);
        Optional<Long> id = issueService.getIssueIdFromName("NP-3");
        assertTrue(id.isEmpty());
    }
}