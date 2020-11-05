package redmine.rest.api.service.timeEntry;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import redmine.rest.api.model.jira.JiraWorkLog;
import redmine.rest.api.model.redmineData.PostTimeEntry;
import redmine.rest.api.model.redmineData.PostTimeEntryData;
import redmine.rest.api.model.redmineData.TimeEntryData;
import redmine.rest.api.service.activity.ActivityService;
import redmine.rest.api.service.issue.IssueService;
import redmine.rest.api.service.user.UserService;

@Service
public class RedmineTimeEntryService implements TimeEntryService {

    private final int SECONDS_IN_MINUTE = 60;
    private final int MINUTES_IN_HOUR = 60;

    private final String url;
    private final HttpHeaders httpHeader;
    private final IssueService issueService;
    private final UserService userService;
    private final ActivityService activityService;
    private RestTemplate restTemplate;

    public RedmineTimeEntryService(RestTemplate restTemplate,
                                   @Value("${redmine.url}") String url,
                                   IssueService issueService,
                                   UserService userService,
                                   ActivityService activityService) {
        this.restTemplate = restTemplate;
        this.issueService = issueService;
        this.userService = userService;
        this.activityService = activityService;
        this.url = url + "/time_entries.json";
        this.httpHeader = new HttpHeaders();
        httpHeader.setContentType(MediaType.APPLICATION_JSON);
    }

    @Override
    public TimeEntryData getTimeEntries() {
        return restTemplate.getForObject(url, TimeEntryData.class);
    }

    @Override
    public PostTimeEntryData postJiraWorkLog(JiraWorkLog jiraWorkLog) {
        PostTimeEntry postTimeEntry = PostTimeEntry.builder()
                .issue_id(issueService.getIssueFromName(jiraWorkLog.getIssue().getKey()))
                .user_id(userService.findUserIdByName(jiraWorkLog.getAuthor().getDisplayName()))
                .hours(secondsToHoursConverter(jiraWorkLog.getTimeSpentSeconds()))
                .comments(jiraWorkLog.getDescription())
                .activity_id(activityService.findActivityFromName(jiraWorkLog.getDescription()))
                .build();
        PostTimeEntryData timeEntry = new PostTimeEntryData(postTimeEntry);

        return restTemplate.postForObject(url, timeEntry, PostTimeEntryData.class);
    }

    private double secondsToHoursConverter(int seconds) {
        return (double) seconds / SECONDS_IN_MINUTE / MINUTES_IN_HOUR;
    }

}
