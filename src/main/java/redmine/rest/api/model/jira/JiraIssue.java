package redmine.rest.api.model.jira;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class JiraIssue {

    private String self;
    private String key;
    private Long id;

}
