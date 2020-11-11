package redmine.rest.api.service.time_entry;

import redmine.rest.api.model.TimeEntry;
import redmine.rest.api.model.jira.JiraPackage;
import redmine.rest.api.model.jira.JiraWorkLog;
import redmine.rest.api.model.redmine_data.TimeEntryData;

import java.util.ArrayList;
import java.util.Optional;

public interface TimeEntryService {

    TimeEntryData getTimeEntries();

    Optional<TimeEntry> postJiraWorkLog(JiraWorkLog timeEntry);

    Optional<ArrayList<TimeEntry>> postJiraWorkLogs(JiraPackage jiraPackage);

}
