package redmine.rest.api.model.jira;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class JiraAuthor {

    private String self;
    private String accountId;
    private String displayName;

}
