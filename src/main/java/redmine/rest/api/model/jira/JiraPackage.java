package redmine.rest.api.model.jira;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class JiraPackage {

    private String self;
    private JiraMetadata metadata;
    private Set<JiraResult> results;

}
