package redmine.rest.api.model.jira;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
public class JiraWorkLog {

    private JiraIssue issue;
    private int timeSpentSeconds;
    private Date startDate;
    private String description;
    private JiraAuthor author;

}
