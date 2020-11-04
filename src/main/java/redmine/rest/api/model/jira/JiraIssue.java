package redmine.rest.api.model.jira;

import lombok.Data;

@Data
public class JiraIssue {

    private String self;
    private String key;
    private Long id;

}
