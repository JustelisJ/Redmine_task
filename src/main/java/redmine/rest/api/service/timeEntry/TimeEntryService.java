package redmine.rest.api.service.timeEntry;

import redmine.rest.api.model.TimeEntry;
import redmine.rest.api.model.jira.JiraWorkLog;
import redmine.rest.api.model.redmineData.TimeEntryData;

import java.util.Optional;

public interface TimeEntryService {

    TimeEntryData getTimeEntries();

    Optional<TimeEntry> postJiraWorkLog(JiraWorkLog timeEntry);

}
