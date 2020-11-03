package redmine.rest.api.model.jira;

import lombok.*;

import java.util.Set;

@Data
public class JiraAttributes {

    private String self;
    private Set<String> values;

}
