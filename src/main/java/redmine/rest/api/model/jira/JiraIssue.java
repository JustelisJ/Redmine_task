package redmine.rest.api.model.jira;

import lombok.*;

@Data
public class JiraIssue {

    private String self;
    private String key;
    private Long id;

}
