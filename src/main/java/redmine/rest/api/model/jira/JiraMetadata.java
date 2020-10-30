package redmine.rest.api.model.jira;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class JiraMetadata {

    private int count;
    private int offset;
    private int limit;

}
