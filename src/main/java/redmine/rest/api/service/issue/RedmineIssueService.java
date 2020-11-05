package redmine.rest.api.service.issue;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import redmine.rest.api.exception.NoIssueFoundException;
import redmine.rest.api.model.Issue;
import redmine.rest.api.model.redmineData.IssueData;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class RedmineIssueService implements IssueService {

    private final String url;
    private RestTemplate restTemplate;
    private Map<String, Long> issues;

    public RedmineIssueService(RestTemplate restTemplate,
                               @Value("${redmine.url}") String url) {
        this.restTemplate = restTemplate;
        this.url = url + "/issues.json";
        issues = new HashMap<>();
        mapAllIssues();
    }

    @Override
    public Optional<Long> getIssueFromName(String name) { //TODO: JSON'e nera pavadinimo, reik ieskot pagal key
        Long id = issues.get(name);
        if (id == null) {
            mapAllIssues();
            id = issues.get(name);
            if (id == null) {
                throw new NoIssueFoundException();

            } else {
                return Optional.of(id);
            }
        } else {
            return Optional.of(id);
        }
    }

    private IssueData getIssues() {
        return restTemplate.getForObject(url, IssueData.class);
    }

    private void mapAllIssues() {
        IssueData issueData = getIssues();
        for (Issue issue : issueData.getIssues()) {
            issues.put(issue.getSubject(), issue.getId());
        }
    }
}
