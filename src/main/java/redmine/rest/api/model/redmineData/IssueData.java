package redmine.rest.api.model.redmineData;

import lombok.Data;
import redmine.rest.api.model.Issue;

import java.util.Set;

@Data
public class IssueData extends Metadata {

    private Set<Issue> issues;

}
