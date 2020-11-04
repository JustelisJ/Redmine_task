package redmine.rest.api.model.jira;

import lombok.Data;

@Data
public class JiraMetadata {

    private int count;
    private int offset;
    private int limit;

}
