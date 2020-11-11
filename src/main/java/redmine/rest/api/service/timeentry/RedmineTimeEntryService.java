package redmine.rest.api.service.timeentry;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import redmine.rest.api.jsonwritter.FailedPostWritterToFile;
import redmine.rest.api.model.TimeEntry;
import redmine.rest.api.model.jira.JiraPackage;
import redmine.rest.api.model.jira.JiraWorkLog;
import redmine.rest.api.model.redminedata.PostTimeEntry;
import redmine.rest.api.model.redminedata.TimeEntryData;
import redmine.rest.api.service.activity.ActivityService;
import redmine.rest.api.service.issue.IssueService;
import redmine.rest.api.service.user.UserService;

import java.util.ArrayList;
import java.util.Optional;

@Log4j2
@Service
public class RedmineTimeEntryService implements TimeEntryService {

    private static final int SECONDS_IN_MINUTE = 60;
    private static final int MINUTES_IN_HOUR = 60;
    public static final String COULDNT_POST_JIRA_WORK_LOG = "Couldn't post JiraWorkLog. ";

    private final String url;
    private final IssueService issueService;
    private final UserService userService;
    private final ActivityService activityService;

    private final RestTemplate restTemplate;
    private FailedPostWritterToFile writter;

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
        writter = new FailedPostWritterToFile();
    }

    @Override
    public TimeEntryData getTimeEntries() {
        return restTemplate.getForObject(url, TimeEntryData.class);
    }

    @Override
    public Optional<TimeEntry> postJiraWorkLog(JiraWorkLog jiraWorkLog) {
        try {
            Optional<Long> issueId = issueService.getIssueIdFromName(jiraWorkLog.getIssue().getKey());
            Optional<Long> userId = userService.findUserIdByName(jiraWorkLog.getAuthor().getDisplayName());
            Optional<Long> activityId = activityService.findActivityFromName(jiraWorkLog.getDescription());
            if (issueId.isPresent() && userId.isPresent() && activityId.isPresent()) {
                PostTimeEntry timeEntry = PostTimeEntry.builder()
                        .issueId(issueId.get())
                        .userId(userId.get())
                        .hours(secondsToHoursConverter(jiraWorkLog.getTimeSpentSeconds()))
                        .comments(jiraWorkLog.getDescription())
                        .activityId(activityId.get())
                        .spentOn(jiraWorkLog.getStartDate())
                        .build();
                return Optional.of(restTemplate.postForObject(url, timeEntry, TimeEntry.class));
            } else {
                log.error(COULDNT_POST_JIRA_WORK_LOG + jiraWorkLog.toString());
                return Optional.empty();
            }
        } catch (Exception e) {
            log.error(COULDNT_POST_JIRA_WORK_LOG + jiraWorkLog.toString());
            writter.logWrongJSONToFile(jiraWorkLog, e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<ArrayList<TimeEntry>> postJiraWorkLogs(JiraPackage jiraPackage) {
        ArrayList<TimeEntry> postedEntries = new ArrayList<>();
        ArrayList<JiraWorkLog> failedPosts = new ArrayList<>();
        ArrayList<Exception> postMessages = new ArrayList<>();
        for (JiraWorkLog workLog : jiraPackage.getWorkLogs()) {
            try {
                Optional<Long> issueId = issueService.getIssueIdFromName(workLog.getIssue().getKey());
                Optional<Long> userId = userService.findUserIdByName(workLog.getAuthor().getDisplayName());
                Optional<Long> activityId = activityService.findActivityFromName(workLog.getDescription());
                if (issueId.isPresent() && userId.isPresent() && activityId.isPresent()) {
                    PostTimeEntry timeEntry = PostTimeEntry.builder()
                            .issueId(issueId.get())
                            .userId(userId.get())
                            .hours(secondsToHoursConverter(workLog.getTimeSpentSeconds()))
                            .comments(workLog.getDescription())
                            .activityId(activityId.get())
                            .spentOn(workLog.getStartDate())
                            .build();
                    postedEntries.add(restTemplate.postForObject(url, timeEntry, TimeEntry.class));
                } else {
                    log.error(COULDNT_POST_JIRA_WORK_LOG + workLog.toString());
                }
            } catch (Exception e) {
                failedPosts.add(workLog);
                postMessages.add(e);
            }
        }
        if (!failedPosts.isEmpty()) {
            writter.logWrongJSONToFile(failedPosts, postMessages);
        }
        return Optional.of(postedEntries);
    }

    private double secondsToHoursConverter(int seconds) {
        return (double) seconds / SECONDS_IN_MINUTE / MINUTES_IN_HOUR;
    }
}
