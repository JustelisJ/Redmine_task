package redmine.rest.api.service.issue;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import redmine.rest.api.model.Issue;
import redmine.rest.api.model.redminedata.IssueData;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Log4j2
@Service
public class RedmineIssueService implements IssueService {

    private static final String MAPPED_ISSUES_MESSAGE = "Mapped all issues";
    private static final String COULDNT_MAP_MESSAGE = "Couldn't map issues";

    private final String url;
    private final RestTemplate restTemplate;
    private Map<String, Long> issues;

    public RedmineIssueService(RestTemplate restTemplate,
                               @Value("${redmine.url}") String url) {
        this.restTemplate = restTemplate;
        this.url = url + "/issues.json";
        issues = new HashMap<>();
        mapAllIssues();
    }

    @Override
    public Optional<Long> getIssueIdFromName(String name) {
        return Optional.ofNullable(issues.get(name));
    }

    private IssueData getIssues() {
        return restTemplate.getForObject(url, IssueData.class);
    }

    @Scheduled(fixedRate = 300000)
    private void mapAllIssues() {
        IssueData issueData = getIssues();
        if (issueData != null) {
            for (Issue issue : issueData.getIssues()) {
                issues.put(issue.getSubject(), issue.getId());
            }
            log.info(MAPPED_ISSUES_MESSAGE);
        } else {
            log.error(COULDNT_MAP_MESSAGE);
        }
    }
}
