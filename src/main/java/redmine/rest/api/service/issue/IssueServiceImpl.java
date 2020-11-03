package redmine.rest.api.service.issue;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import redmine.rest.api.model.Issue;
import redmine.rest.api.model.User;
import redmine.rest.api.model.redmineData.IssueData;
import redmine.rest.api.model.redmineData.UserData;

import java.util.HashMap;
import java.util.Map;

@Service
public class IssueServiceImpl implements IssueService {

    private RestTemplate restTemplate;
    private final String url;
    private Map<String, Long> issues;

    public IssueServiceImpl(RestTemplate restTemplate,
                            @Value("${redmine.url}") String url) {
        this.restTemplate = restTemplate;
        this.url = url+"/issues.json";
        issues = new HashMap<>();
    }

    @Override
    public IssueData getIssues() {
        return restTemplate.getForObject(url, IssueData.class);
    }

    @Override
    public Long getIssueFromName(String name) { //TODO: JSON'e nera pavadinimo, reik ieskot pagal key
        Long id = issues.get(name);
        if(id == null){
            IssueData issueData = getIssues();
            for (Issue issue:issueData.getIssues()) {
                issues.put(issue.getSubject(), issue.getId());
            }
            id = issues.get(name);
            if(id == null){
                throw new RuntimeException("No such issue exists");
            } else {
                return id;
            }
        } else {
            return id;
        }
    }
}
