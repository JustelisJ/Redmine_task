package redmine.rest.api.service.timeEntry;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import redmine.rest.api.model.TimeEntry;
import redmine.rest.api.model.jira.JiraResult;
import redmine.rest.api.model.redmineData.PostTimeEntryData;
import redmine.rest.api.model.redmineData.TimeEntryData;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TimeEntryServiceImplTest {

    @Mock
    TimeEntryService entryService;
    Set<TimeEntry> timeEntries;
    TimeEntryData data;
    PostTimeEntryData postData;

    @BeforeEach
    void setUp() {
        postData = PostTimeEntryData.builder().user_id(7L).hours("2.5").
                issue_id(3L).comments("stuff").build();
        TimeEntry entry1 = TimeEntry.builder().id(1L).hours(2).build();
        TimeEntry entry2 = TimeEntry.builder().id(2L).hours(1).build();
        timeEntries = new HashSet<>();
        timeEntries.add(entry1);
        timeEntries.add(entry2);
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
        when(entryService.postTimeEntry(any())).thenReturn(postData);

        PostTimeEntryData returnedPost =
                entryService.postTimeEntry(new JiraResult());

        assertNotNull(returnedPost);
        assertEquals(returnedPost, postData);
        verify(entryService).postTimeEntry(any());
    }
}