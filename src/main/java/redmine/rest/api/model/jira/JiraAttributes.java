package redmine.rest.api.model.jira;

import lombok.Data;

import java.util.Set;

@Data
public class JiraAttributes {

    private String self;
    private Set<String> values;

}
