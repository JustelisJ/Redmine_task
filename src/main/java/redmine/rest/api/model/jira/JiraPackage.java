package redmine.rest.api.model.jira;

import lombok.*;

import java.util.Set;

@Data
public class JiraPackage {

    private String self;
    private JiraMetadata metadata;
    private Set<JiraResult> results;

}
