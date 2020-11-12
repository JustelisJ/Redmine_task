package redmine.rest.api.service.timeentry;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import redmine.rest.api.json.model.FailedPost;
import redmine.rest.api.json.writter.FailedPostWriterToFile;
import redmine.rest.api.model.TimeEntry;
import redmine.rest.api.model.jira.JiraPackage;
import redmine.rest.api.model.jira.JiraWorkLog;
import redmine.rest.api.model.redminedata.PostTimeEntry;
import redmine.rest.api.model.redminedata.TimeEntryData;
import redmine.rest.api.service.activity.ActivityService;
import redmine.rest.api.service.issue.IssueService;
import redmine.rest.api.service.user.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Log4j2
@Service
public class RedmineTimeEntryService implements TimeEntryService {

    private static final int SECONDS_IN_MINUTE = 60;
    private static final int MINUTES_IN_HOUR = 60;
    private static final String COULDNT_POST_JIRA_WORK_LOG = "Couldn't post JiraWorkLog. ";

    private final String url;
    private final IssueService issueService;
    private final UserService userService;
    private final ActivityService activityService;

    private final RestTemplate restTemplate;
    private final FailedPostWriterToFile writter;

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
        writter = new FailedPostWriterToFile();
    }

    @Override
    public TimeEntryData getTimeEntries() {
        return restTemplate.getForObject(url, TimeEntryData.class);
    }

    @Override
    public Optional<TimeEntry> postJiraWorkLog(JiraWorkLog jiraWorkLog) {
        try {
            PostTimeEntry timeEntry = PostTimeEntry.builder()
                    .issueId(issueService.getIssueIdFromName(jiraWorkLog.getIssue().getKey()))
                    .userId(userService.findUserIdByName(jiraWorkLog.getAuthor().getDisplayName()))
                    .hours(secondsToHoursConverter(jiraWorkLog.getTimeSpentSeconds()))
                    .comments(jiraWorkLog.getDescription())
                    .activityId(activityService.findActivityFromName(jiraWorkLog.getDescription()))
                    .spentOn(jiraWorkLog.getStartDate())
                    .build();
            return Optional.ofNullable(restTemplate.postForObject(url, timeEntry, TimeEntry.class));
        } catch (Exception e) {
            log.error(COULDNT_POST_JIRA_WORK_LOG + jiraWorkLog.toString());
            writter.logWrongJSONToFile(new FailedPost(jiraWorkLog, e));
            return Optional.empty();
        }
    }

    @Override
    public List<TimeEntry> postJiraWorkLogs(JiraPackage jiraPackage) {
        ArrayList<TimeEntry> postedEntries = new ArrayList<>();
        ArrayList<FailedPost> failedPosts = new ArrayList<>();
        for (JiraWorkLog workLog : jiraPackage.getWorkLogs()) {
            try {
                PostTimeEntry timeEntry = PostTimeEntry.builder()
                        .issueId(issueService.getIssueIdFromName(workLog.getIssue().getKey()))
                        .userId(userService.findUserIdByName(workLog.getAuthor().getDisplayName()))
                        .hours(secondsToHoursConverter(workLog.getTimeSpentSeconds()))
                        .comments(workLog.getDescription())
                        .activityId(activityService.findActivityFromName(workLog.getDescription()))
                        .spentOn(workLog.getStartDate())
                        .build();
                postedEntries.add(restTemplate.postForObject(url, timeEntry, TimeEntry.class));
            } catch (Exception e) {
                failedPosts.add(new FailedPost(workLog, e));
            }
        }
        if (!failedPosts.isEmpty()) {
            writter.logWrongJSONToFile(failedPosts);
        }
        return postedEntries;
    }

    private double secondsToHoursConverter(int seconds) {
        return (double) seconds / SECONDS_IN_MINUTE / MINUTES_IN_HOUR;
    }
}
