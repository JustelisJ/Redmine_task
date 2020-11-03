package redmine.rest.api.service.timeEntry;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import redmine.rest.api.model.TimeEntry;
import redmine.rest.api.model.jira.JiraResult;
import redmine.rest.api.model.redmineData.PostTimeEntry;
import redmine.rest.api.model.redmineData.PostTimeEntryData;
import redmine.rest.api.model.redmineData.TimeEntryData;
import redmine.rest.api.service.activity.ActivityService;
import redmine.rest.api.service.issue.IssueService;
import redmine.rest.api.service.user.UserService;

@Service
public class TimeEntryServiceImpl implements TimeEntryService {

    private RestTemplate restTemplate;
    private final String url;
    private final HttpHeaders httpHeader;

    private final IssueService issueService;
    private final UserService userService;
    private final ActivityService activityService;

    public TimeEntryServiceImpl(RestTemplate restTemplate,
                                @Value("${redmine.url}") String url,
                                IssueService issueService,
                                UserService userService,
                                ActivityService activityService) {
        this.restTemplate = restTemplate;
        this.issueService = issueService;
        this.userService = userService;
        this.activityService = activityService;
        this.url = url+"/time_entries.json";
        this.httpHeader = new HttpHeaders();
        httpHeader.setContentType(MediaType.APPLICATION_JSON);
    }

    @Override
    public TimeEntryData getTimeEntries() {
        return restTemplate.getForObject(url, TimeEntryData.class);
    }

    @Override
    public PostTimeEntryData postTimeEntry(JiraResult jiraResult) {
        System.out.println(jiraResult);
        PostTimeEntry postTimeEntry = PostTimeEntry.builder()
                .issue_id(issueService.getIssueFromName(jiraResult.getIssue().getKey()))
                .user_id(userService.findUserIdByName(jiraResult.getAuthor().getDisplayName()))
                .hours(secondsToHoursConverter(jiraResult.getTimeSpentSeconds()))
                .comments(jiraResult.getDescription())
                .activity_id(activityService.findActivityFromName(jiraResult.getDescription()))
                .build();
        PostTimeEntryData timeEntry = new PostTimeEntryData(postTimeEntry);

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            System.out.println(objectMapper.writeValueAsString(timeEntry));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return restTemplate.postForObject(url, timeEntry, PostTimeEntryData.class);
    }

    private double secondsToHoursConverter(int seconds){
        return (double)seconds / 60 / 60;
    }

}
