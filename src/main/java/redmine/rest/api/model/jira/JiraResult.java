package redmine.rest.api.model.jira;

import lombok.*;
import java.util.Date;

@Data
public class JiraResult {

    private String self;
    private Long tempoWorklogId;
    private Long jiraWorklogId;
    private JiraIssue issue;
    private int timeSpentSeconds;
    private int billableSeconds;
    private Date startDate;
    private String startTime;
    private String description;
    private Date createdAt;
    private Date updatedAt;
    private JiraAuthor author;
    private JiraAttributes attributes;

}
