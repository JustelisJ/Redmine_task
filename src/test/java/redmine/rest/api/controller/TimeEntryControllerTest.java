package redmine.rest.api.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import redmine.rest.api.model.TimeEntry;
import redmine.rest.api.model.redmineData.TimeEntryData;
import redmine.rest.api.service.timeEntry.TimeEntryService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class TimeEntryControllerTest {

    @Mock
    TimeEntryService entryService;
    @InjectMocks
    TimeEntryController controller;
    List<TimeEntry> list;
    TimeEntryData data;
    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        list = new ArrayList<>();
        list.add(TimeEntry.builder().id(1L).build());
        list.add(TimeEntry.builder().id(2L).build());
        data = TimeEntryData.builder().time_entries(list).build();
    }

    @Test
    void getIssues() throws Exception {
        when(entryService.getTimeEntries()).thenReturn(data);
        mockMvc.perform(get("/timeEntries"))
                .andExpect(status().isOk())
                .andExpect(view().name("timeEntry/allTimeEntries"))
                .andExpect(model().attributeExists("timeEntries"));
    }
}