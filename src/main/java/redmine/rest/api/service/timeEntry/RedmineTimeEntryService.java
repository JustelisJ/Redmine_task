package redmine.rest.api.service.timeEntry;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import redmine.rest.api.model.TimeEntry;
import redmine.rest.api.model.jira.JiraWorkLog;
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
    }

    @Override
    public TimeEntryData getTimeEntries() {
        return restTemplate.getForObject(url, TimeEntryData.class);
    }

    @Override
    public TimeEntry postJiraWorkLog(JiraWorkLog jiraWorkLog) {
        //TODO: Vietoj exception, kad grazintu tuscia Optional ir
        // kad praleistu arba kazkur surasytu klaidingus entries
        PostTimeEntryData timeEntry = PostTimeEntryData.builder()
                .issue_id(issueService.getIssueFromName(jiraWorkLog.getIssue().getKey()).get())
                .user_id(userService.findUserIdByName(jiraWorkLog.getAuthor().getDisplayName()).get())
                .hours(secondsToHoursConverter(jiraWorkLog.getTimeSpentSeconds()))
                .comments(jiraWorkLog.getDescription())
                .activity_id(activityService.findActivityFromName(jiraWorkLog.getDescription()).get())
                .build();

        return restTemplate.postForObject(url, timeEntry, TimeEntry.class);
    }

    private double secondsToHoursConverter(int seconds) {
        return (double) seconds / SECONDS_IN_MINUTE / MINUTES_IN_HOUR;
    }

}
