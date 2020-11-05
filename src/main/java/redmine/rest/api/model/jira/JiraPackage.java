package redmine.rest.api.model.jira;

import lombok.Data;

import java.util.Set;

@Data
public class JiraPackage {

    private String self;
    private JiraMetadata metadata;
    private Set<JiraWorkLog> workLogs;

}
