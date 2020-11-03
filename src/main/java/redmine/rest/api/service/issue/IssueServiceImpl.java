package redmine.rest.api.service.issue;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import redmine.rest.api.model.Issue;
import redmine.rest.api.model.redmineData.IssueData;

import java.util.HashMap;
import java.util.Map;

@Service
public class IssueServiceImpl implements IssueService {

    private RestTemplate restTemplate;
    private final String url;
    private Map<String, Long> issues;

    public IssueServiceImpl(RestTemplate restTemplate, @Value("${redmine.url}") String url) {
        this.restTemplate = restTemplate;
        this.url = url+"/issues.json";
        issues = new HashMap<>();
    }

    @Override
    public IssueData getIssues() {
        return restTemplate.getForObject(url, IssueData.class);
    }

    @Override
    public Long getIssueFromName(String name) {
        try{
            return issues.get(name);
        }
        catch (Exception e){
            IssueData data = getIssues();
            for (Issue issue:data.getIssues()) {
                issues.put(issue.getSubject(), issue.getId());
            }

            //try to return again, if the key does not exist, throw runTimeException
            try{
                return issues.get(name);
            }
            catch (Exception e1){
                throw new RuntimeException("No such issue exists");
            }
        }
    }
}
