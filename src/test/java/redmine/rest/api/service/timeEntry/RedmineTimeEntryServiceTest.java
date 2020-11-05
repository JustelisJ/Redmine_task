package redmine.rest.api.service.timeEntry;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import redmine.rest.api.model.TimeEntry;
import redmine.rest.api.model.redmineData.PostTimeEntryData;
import redmine.rest.api.model.redmineData.TimeEntryData;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RedmineTimeEntryServiceTest {

    @Mock
    TimeEntryService entryService;
    List<TimeEntry> timeEntries;
    TimeEntryData data;
    PostTimeEntryData postData;

    @BeforeEach
    void setUp() {
        PostTimeEntryData postData = PostTimeEntryData.builder()
                .issue_id(3L)
                .user_id(7L)
                .hours(2)
                .comments("hehe")
                .activity_id(null)
                .build();
        //postData = PostTimeEntryData.builder().time_entry(postTimeEntry).build();
        TimeEntry entry1 = TimeEntry.builder().id(1L).hours(2).build();
        TimeEntry entry2 = TimeEntry.builder().id(2L).hours(1).build();
        timeEntries = new ArrayList<>();
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
//        when(entryService.postJiraWorkLog(any())).thenReturn(postData);
//
//        TimeEntry returnedPost =
//                entryService.postJiraWorkLog(new JiraWorkLog());
//
//        assertNotNull(returnedPost);
//        assertEquals(returnedPost, postData);
//        verify(entryService).postJiraWorkLog(any());
    }
}