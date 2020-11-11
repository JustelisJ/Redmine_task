package redmine.rest.api.service.issue;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import redmine.rest.api.exception.IssueNotFoundException;
import redmine.rest.api.model.Issue;
import redmine.rest.api.model.redmine_data.IssueData;

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
        Long id = issues.getOrDefault(name, null);
        if (id == null) {
            throw new IssueNotFoundException();
        } else {
            return Optional.of(id);
        }
    }

    private Optional<IssueData> getIssues() {
        return Optional.of(restTemplate.getForObject(url, IssueData.class));
    }

    @Scheduled(fixedRate = 300000)
    private void mapAllIssues() {
        Optional<IssueData> optionalIssueData = getIssues();
        if (optionalIssueData.isPresent()) {
            IssueData issueData = optionalIssueData.get();
            for (Issue issue : issueData.getIssues()) {
                issues.put(issue.getSubject(), issue.getId());
            }
            log.info(MAPPED_ISSUES_MESSAGE);
        } else {
            log.warn(COULDNT_MAP_MESSAGE);
        }
    }
}
