package redmine.rest.api.model.jira;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class JiraAuthor {

    private String self;
    private String accountId;
    private String displayName;

}
