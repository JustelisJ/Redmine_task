package redmine.rest.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import redmine.rest.api.config.JacksonConfig;
import redmine.rest.api.model.TimeEntry;
import redmine.rest.api.model.jira.JiraPackage;
import redmine.rest.api.model.jira.JiraWorkLog;
import redmine.rest.api.model.redminedata.TimeEntryData;
import redmine.rest.api.service.timeentry.TimeEntryService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@Import(JacksonConfig.class)
class TimeEntryControllerTest {

    private static final String GET_ISSUES_URL = "/timeEntries";
    private static final String ISSUES_VIEW_NAME = "timeEntry/allTimeEntries";
    private static final String TIME_ENTRY_ATTRIBUTE_NAME = "timeEntries";
    private static final String TIME_ENTRIES_NEW_URL = "/timeEntries/new";
    private static final String REDIRECT_TIME_ENTRIES_URL = "redirect:/timeEntries";

    @Mock
    TimeEntryService entryService;
    @InjectMocks
    TimeEntryController controller;

    TimeEntryData timeEntryData;
    JiraPackage jiraPackage;

    @Autowired
    ObjectMapper mapper;
    MockMvc mockMvc;

    private void initializeMock() {
        controller = new TimeEntryController(entryService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    private void initializeData() {
        initializeTimeEntryData();

        initializeJiraPackage();
    }

    private void initializeJiraPackage() {
        JiraWorkLog workLog = new JiraWorkLog();
        workLog.setAccountId("5f9a8e6762584c006be3447c");
        workLog.setDescription("Working on issue TP-1");
        workLog.setTimeSpentSeconds(3600);
        workLog.setStartDate("2020-10-31");
        workLog.setIssueId("NP-1");

        Set<JiraWorkLog> workLogs = new HashSet<>();
        workLogs.add(workLog);
        jiraPackage = new JiraPackage();
        jiraPackage.setWorkLogs(workLogs);
    }

    private void initializeTimeEntryData() {
        List<TimeEntry> list = new ArrayList<>();
        list.add(TimeEntry.builder().id(1L).build());
        list.add(TimeEntry.builder().id(2L).build());
        timeEntryData = TimeEntryData.builder().timeEntries(list).build();
    }

    @BeforeEach
    void setUp() {
        initializeMock();
        initializeData();
    }

    @Test
    void getIssues() throws Exception {
        when(entryService.getTimeEntries()).thenReturn(timeEntryData);
        mockMvc.perform(get(GET_ISSUES_URL))
                .andExpect(status().isOk())
                .andExpect(view().name(ISSUES_VIEW_NAME))
                .andExpect(model().attributeExists(TIME_ENTRY_ATTRIBUTE_NAME));
        verify(entryService, times(1)).getTimeEntries();
    }

    @Test
    void createTimeEntryFromJSON() throws Exception {
        mockMvc.perform(post(TIME_ENTRIES_NEW_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(jiraPackage))
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name(REDIRECT_TIME_ENTRIES_URL));
        verify(entryService, times(1)).postJiraWorkLogs(any(JiraPackage.class));
    }
}