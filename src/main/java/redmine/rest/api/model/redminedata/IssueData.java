package redmine.rest.api.model.redminedata;

import lombok.Data;
import redmine.rest.api.model.Issue;

import java.util.List;

@Data
public class IssueData extends Metadata {

    private List<Issue> issues;

}
