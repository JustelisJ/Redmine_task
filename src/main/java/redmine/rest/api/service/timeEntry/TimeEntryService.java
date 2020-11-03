package redmine.rest.api.service.timeEntry;

import redmine.rest.api.model.jira.JiraResult;
import redmine.rest.api.model.redmineData.PostTimeEntryData;
import redmine.rest.api.model.redmineData.TimeEntryData;

public interface TimeEntryService {

    TimeEntryData getTimeEntries();
    PostTimeEntryData postTimeEntry(JiraResult timeEntry);

}
