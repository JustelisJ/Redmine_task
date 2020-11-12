package redmine.rest.api.json.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import redmine.rest.api.model.jira.JiraWorkLog;

@Data
@AllArgsConstructor
public class FailedPost {

    JiraWorkLog workLog;
    String exception;

}
