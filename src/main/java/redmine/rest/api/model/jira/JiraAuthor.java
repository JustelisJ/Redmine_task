package redmine.rest.api.model.jira;

import lombok.*;

@Data
public class JiraAuthor {

    private String self;
    private String accountId;
    private String displayName;

}
