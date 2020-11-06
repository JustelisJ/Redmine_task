package redmine.rest.api.service.issue;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import redmine.rest.api.exception.NoIssueFoundException;
import redmine.rest.api.model.Issue;
import redmine.rest.api.model.redmineData.IssueData;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Log4j2
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
    public Optional<Long> getIssueIdFromName(String name) throws NoIssueFoundException {
        Long id = issues.getOrDefault(name, null);
        if (id == null) {
            throw new NoIssueFoundException();
        } else {
            return Optional.of(id);
        }
    }

    private IssueData getIssues() {
        return restTemplate.getForObject(url, IssueData.class);
    }

    @Scheduled(fixedRate = 300000)
    private void mapAllIssues() {
        IssueData issueData = getIssues();
        for (Issue issue : issueData.getIssues()) {
            issues.put(issue.getSubject(), issue.getId());
        }
        log.info("Mapped all issues");
    }
}
