package redmine.rest.api.model.jira;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import redmine.rest.api.model.redmineData.Metadata;

import java.sql.Timestamp;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@ToString
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
