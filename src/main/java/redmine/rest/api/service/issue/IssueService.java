package redmine.rest.api.service.issue;

import redmine.rest.api.exception.NoIssueFoundException;

import java.util.Optional;

public interface IssueService {

    Optional<Long> getIssueIdFromName(String name) throws NoIssueFoundException;

}
