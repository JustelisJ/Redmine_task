package redmine.rest.api.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import redmine.rest.api.model.TimeEntry;
import redmine.rest.api.model.redmine_data.TimeEntryData;
import redmine.rest.api.service.time_entry.TimeEntryService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class TimeEntryControllerTest {

    private static final String GET_ISSUES_URL = "/timeEntries";
    private static final String ISSUES_VIEW_NAME = "timeEntry/allTimeEntries";
    private static final String TIME_ENTRY_ATTRIBUTE_NAME = "timeEntries";

    @Mock
    TimeEntryService entryService;
    @InjectMocks
    TimeEntryController controller;
    TimeEntryData data;
    MockMvc mockMvc;

    private void initializeMock() {
        MockitoAnnotations.initMocks(this);
        controller = new TimeEntryController(entryService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    private void initializeData() {
        List<TimeEntry> list = new ArrayList<>();
        list.add(TimeEntry.builder().id(1L).build());
        list.add(TimeEntry.builder().id(2L).build());
        data = TimeEntryData.builder().timeEntries(list).build();
    }

    @BeforeEach
    void setUp() {
        initializeMock();
        initializeData();
    }

    @Test
    void getIssues() throws Exception {
        when(entryService.getTimeEntries()).thenReturn(data);
        mockMvc.perform(get(GET_ISSUES_URL))
                .andExpect(status().isOk())
                .andExpect(view().name(ISSUES_VIEW_NAME))
                .andExpect(model().attributeExists(TIME_ENTRY_ATTRIBUTE_NAME));
    }
}