package redmine.rest.api.model.jira;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Set;

@Data
public class JiraPackage {

    private String self;
    private JiraMetadata metadata;
    @JsonProperty("results")
    private Set<JiraWorkLog> workLogs;

}
