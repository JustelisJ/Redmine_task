package redmine.rest.api.service.timeEntry;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import redmine.rest.api.model.Activity;
import redmine.rest.api.model.Issue;
import redmine.rest.api.model.TimeEntry;
import redmine.rest.api.model.User;
import redmine.rest.api.model.jira.JiraWorkLog;
import redmine.rest.api.model.redmineData.TimeEntryData;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class RedmineTimeEntryServiceTest {

    @Mock
    TimeEntryService entryService;
    List<TimeEntry> timeEntries;
    TimeEntryData data;
    Optional<TimeEntry> responseData;

    @BeforeEach
    void setUp() {
        responseData = Optional.of(TimeEntry.builder()
                .issue(Issue.builder().id(3L).build())
                .user(User.builder().id(7L).firstname("Baubas").build())
                .hours(2)
                .comments("hehe")
                .activity(Activity.builder().id(6L).build())
                .build());
        timeEntries = new ArrayList<>();
        timeEntries.add(redmine.rest.api.model.TimeEntry.builder().id(1L).hours(2).build());
        timeEntries.add(redmine.rest.api.model.TimeEntry.builder().id(2L).hours(1).build());
        data = TimeEntryData.builder().time_entries(timeEntries).build();
    }

    @Test
    void getTimeEntries() {
        when(entryService.getTimeEntries()).thenReturn(data);
        TimeEntryData returnedData = entryService.getTimeEntries();

        assertEquals(returnedData.getTime_entries().size(), 2);
    }

    @Test
    void postTimeEntry() {
        when(entryService.postJiraWorkLog(any())).thenReturn(responseData);

        TimeEntry returnedPost =
                entryService.postJiraWorkLog(new JiraWorkLog()).get();

        assertNotNull(returnedPost);
        assertEquals(returnedPost, responseData);
        assertEquals(returnedPost.getUser().getFirstname(), "Baubas");
        verify(entryService).postJiraWorkLog(any());
    }
}