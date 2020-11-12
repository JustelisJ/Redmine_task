package redmine.rest.api.service.issue;

import java.util.Optional;

public interface IssueService {

    Optional<Long> getIssueIdFromName(String name);

}
