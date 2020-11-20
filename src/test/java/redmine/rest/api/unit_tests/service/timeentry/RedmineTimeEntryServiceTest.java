package redmine.rest.api.unit_tests.service.timeentry;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.web.client.RestTemplate;
import redmine.rest.api.config.RestTemplateConfig;
import redmine.rest.api.exception.IssueNotFoundException;
import redmine.rest.api.exception.UserNotFoundException;
import redmine.rest.api.model.TimeEntry;
import redmine.rest.api.model.jira.JiraPackage;
import redmine.rest.api.model.jira.JiraWorkLog;
import redmine.rest.api.model.redminedata.TimeEntryData;
import redmine.rest.api.service.activity.ActivityService;
import redmine.rest.api.service.issue.IssueService;
import redmine.rest.api.service.timeentry.RedmineTimeEntryService;
import redmine.rest.api.service.timeentry.TimeEntryService;
import redmine.rest.api.service.user.UserService;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Import(RestTemplateConfig.class)
@SpringBootTest
class RedmineTimeEntryServiceTest {

    private static final long ISSUE_ID = 3L;
    private static final long USER_ID = 7L;
    private static final long ACTIVITY_ID = 6L;

    @Mock
    IssueService issueService;
    @Mock
    UserService userService;
    @Mock
    ActivityService activityService;
    @Autowired
    RestTemplate restTemplate;

    TimeEntryService entryService;

    JiraWorkLog workLog;
    JiraPackage jiraPackage;

    private void initializeData() {
        initializeJiraWorkLog();

        initializeJiraPackage();
    }

    private void initializeJiraPackage() {
        Set<JiraWorkLog> workLogs = new HashSet<>();
        workLogs.add(workLog);
        jiraPackage = new JiraPackage();
        jiraPackage.setWorkLogs(workLogs);
    }

    private void initializeJiraWorkLog() {
        workLog = new JiraWorkLog();
        workLog.setAccountId("asdasd");
        workLog.setDescription("smth");
        workLog.setIssueId("idk");
        workLog.setStartDate("2020-10-31");
        workLog.setTimeSpentSeconds(3600);
        workLog.setProjectKey("null");
    }

    @BeforeEach
    void setUp() {
        initializeData();

        entryService = new RedmineTimeEntryService(restTemplate,
                issueService, userService, activityService);
    }

    @Test
    void getTimeEntries() {
        TimeEntryData returnedData = entryService.getTimeEntries();

        assertFalse(returnedData.getTimeEntries().isEmpty());
    }

    @Test
    void postJiraWorkLog() {
        when_thenStatementsForPost();

        Optional<TimeEntry> returnedPost =
                entryService.postJiraWorkLog(workLog);

        assertTrue(returnedPost.isPresent());
        assertEquals(ACTIVITY_ID, returnedPost.get().getActivity().getId());
    }

    @Test
    void postJiraWorkLogCantFindIssue() {
        when(issueService.getIssueIdFromName(anyString())).thenThrow(IssueNotFoundException.class);

        assertThrows(IssueNotFoundException.class, () -> {
            entryService.postJiraWorkLog(workLog);
        });
    }

    @Test
    void postJiraWorkLogCantFindUser() {
        when(userService.findUserIdByName(anyString())).thenThrow(UserNotFoundException.class);

        assertThrows(UserNotFoundException.class, () -> {
            entryService.postJiraWorkLog(workLog);
        });
    }

    @Test
    void postTimeEntries() {
        when_thenStatementsForPost();

        List<TimeEntry> returnedPosts =
                entryService.postJiraWorkLogs(jiraPackage);

        assertFalse(returnedPosts.isEmpty());
        assertEquals(ACTIVITY_ID, returnedPosts.get(0).getActivity().getId());
    }

    private void when_thenStatementsForPost() {
        when(issueService.getIssueIdFromName(anyString())).thenReturn(Optional.of(ISSUE_ID));
        when(userService.findUserIdByName(anyString())).thenReturn(Optional.of(USER_ID));
        when(activityService.findActivityFromName(anyString())).thenReturn(ACTIVITY_ID);
    }
}
