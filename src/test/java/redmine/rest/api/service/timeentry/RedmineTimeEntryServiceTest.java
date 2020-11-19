package redmine.rest.api.service.timeentry;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import redmine.rest.api.model.Activity;
import redmine.rest.api.model.Issue;
import redmine.rest.api.model.TimeEntry;
import redmine.rest.api.model.User;
import redmine.rest.api.model.jira.JiraPackage;
import redmine.rest.api.model.jira.JiraWorkLog;
import redmine.rest.api.model.redminedata.TimeEntryData;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class RedmineTimeEntryServiceTest {

    private static final String TIME_ENTRY_FIRSTNAME = "Baubas";
    private static final String TIME_ENTRY_COMMENT = "hehe";
    private static final int TIME_ENTRY_DATA_SIZE = 2;

    @InjectMocks
    TimeEntryService entryService;

    TimeEntryData data;
    TimeEntry responseData;
    ArrayList<TimeEntry> responseData2;

    private void initializeData() {
        responseData = TimeEntry.builder()
                .issue(Issue.builder().id(3L).build())
                .user(User.builder().id(7L).firstname(TIME_ENTRY_FIRSTNAME).build())
                .hours(2)
                .comments(TIME_ENTRY_COMMENT)
                .activity(Activity.builder().id(6L).build())
                .build();
        ArrayList<TimeEntry> list = new ArrayList<>();
        list.add(responseData);
        responseData2 = list;
        List<TimeEntry> timeEntries = new ArrayList<>();
        timeEntries.add(redmine.rest.api.model.TimeEntry.builder().id(1L).hours(2).build());
        timeEntries.add(redmine.rest.api.model.TimeEntry.builder().id(2L).hours(1).build());
        data = TimeEntryData.builder().timeEntries(timeEntries).build();
    }

    @BeforeEach
    void setUp() {
        initializeData();
    }

    @Test
    void getTimeEntries() {
        TimeEntryData returnedData = entryService.getTimeEntries();

        assertEquals(TIME_ENTRY_DATA_SIZE, returnedData.getTimeEntries().size());
    }

    @Test
    void postTimeEntry() {
        when(entryService.postJiraWorkLog(any())).thenReturn(Optional.of(responseData));

        Optional<TimeEntry> returnedPost =
                entryService.postJiraWorkLog(new JiraWorkLog());

        assertNotNull(returnedPost);
        assertTrue(returnedPost.isPresent());
        assertEquals(responseData, returnedPost.get());
        assertEquals(TIME_ENTRY_FIRSTNAME, returnedPost.get().getUser().getFirstname());
        verify(entryService).postJiraWorkLog(any());
    }

    @Test
    void postTimeEntries() {
        when(entryService.postJiraWorkLogs(any())).thenReturn(responseData2);

        List<TimeEntry> returnedPost =
                entryService.postJiraWorkLogs(new JiraPackage());

        assertNotNull(returnedPost);
        assertEquals(responseData2, returnedPost);
        assertEquals(TIME_ENTRY_FIRSTNAME, returnedPost.get(0).getUser().getFirstname());
        verify(entryService).postJiraWorkLogs(any());
    }
}
