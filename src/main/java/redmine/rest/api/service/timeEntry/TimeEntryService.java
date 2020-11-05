package redmine.rest.api.service.timeEntry;

import redmine.rest.api.model.TimeEntry;
import redmine.rest.api.model.jira.JiraWorkLog;
import redmine.rest.api.model.redmineData.TimeEntryData;

public interface TimeEntryService {

    TimeEntryData getTimeEntries();

    TimeEntry postJiraWorkLog(JiraWorkLog timeEntry);

}
