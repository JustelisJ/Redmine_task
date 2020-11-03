package redmine.rest.api.service.timeEntry;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import redmine.rest.api.model.TimeEntry;
import redmine.rest.api.model.jira.JiraResult;
import redmine.rest.api.model.redmineData.PostTimeEntryData;
import redmine.rest.api.model.redmineData.TimeEntryData;
import redmine.rest.api.service.issue.IssueService;

@Service
public class TimeEntryServiceImpl implements TimeEntryService {

    private RestTemplate restTemplate;
    private final String url;
    private final HttpHeaders httpHeader;

    private final IssueService issueService;

    public TimeEntryServiceImpl(RestTemplate restTemplate,
                                @Value("${redmine.url}") String url,
                                IssueService issueService) {
        this.restTemplate = restTemplate;
        this.issueService = issueService;
        restTemplate.getInterceptors().add(new BasicAuthenticationInterceptor("admin", "password"));
        this.url = url+"/time_entries.json";
        this.httpHeader = new HttpHeaders();
        httpHeader.setContentType(MediaType.APPLICATION_JSON);
    }

    @Override
    public TimeEntryData getTimeEntries() {
        return restTemplate.getForObject(url, TimeEntryData.class);
    }

    @Override
    public TimeEntry postTimeEntry(JiraResult timeEntry) {
        PostTimeEntryData post = new PostTimeEntryData(
                issueService.getIssueFromName(timeEntry.getIssue().getKey()),//find issue-id by name
                1L,//find user_id by name
                timeEntry.getTimeSpentSeconds()/60/60,  //converted to minutes, then to hours
                "",//Comment needs to be added to JiraResult
                1L//find activity_id by description
        );

        return restTemplate.postForObject(url, post, TimeEntry.class);
    }

}
