package redmine.rest.api.model.jira;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Set;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class JiraPackage {

    @JsonProperty("results")
    private Set<JiraWorkLog> workLogs;

}
