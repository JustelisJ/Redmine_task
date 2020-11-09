package redmine.rest.api.model.jira;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.Date;

@Data
@JsonIgnoreProperties({"self", "tempoWorklogId", "jiraWorklogId", "billableSeconds",
        "startTime", "createdAt", "updatedAt", "attributes", "exception"})
public class JiraWorkLog {

    private JiraIssue issue;
    private int timeSpentSeconds;
    private Date startDate;
    private String description;
    private JiraAuthor author;

}
