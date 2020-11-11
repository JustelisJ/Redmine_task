package redmine.rest.api.service.issue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import redmine.rest.api.exception.IssueNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class RedmineIssueServiceTest {

    private static final String NP_1_ISSUE_NAME = "NP-1";
    private static final String NP_3_ISSUE_NAME = "NP-3";

    @Mock
    IssueService issueService;
    Long issueId;

    private void initializeMock() {
        MockitoAnnotations.initMocks(this);
    }

    private void initializeData() {
        issueId = 2L;
    }

    @BeforeEach
    void setUp() {
        initializeMock();
        initializeData();
    }

    @Test
    void getIssueIdFromName() {
        when(issueService.getIssueIdFromName(NP_1_ISSUE_NAME)).thenReturn(Optional.of(issueId));
        Optional<Long> id = issueService.getIssueIdFromName(NP_1_ISSUE_NAME);
        assertTrue(id.isPresent());
        assertEquals(issueId, id.get());
    }

    @Test
    void dontGetIssueIdFromName() {
        when(issueService.getIssueIdFromName(NP_1_ISSUE_NAME)).thenReturn(Optional.of(issueId));
        when(issueService.getIssueIdFromName(NP_3_ISSUE_NAME)).thenThrow(IssueNotFoundException.class);
        assertThrows(IssueNotFoundException.class, () -> {
            issueService.getIssueIdFromName(NP_3_ISSUE_NAME);
        });
    }
}