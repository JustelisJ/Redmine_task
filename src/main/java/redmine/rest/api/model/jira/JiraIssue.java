package redmine.rest.api.model.jira;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class JiraIssue {

    private String self;
    private String key;
    private Long id;

}
