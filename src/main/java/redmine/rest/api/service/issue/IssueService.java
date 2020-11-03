package redmine.rest.api.service.issue;

import redmine.rest.api.model.redmineData.IssueData;

public interface IssueService {

    IssueData getIssues();
    Long getIssueFromName(String name);

}
