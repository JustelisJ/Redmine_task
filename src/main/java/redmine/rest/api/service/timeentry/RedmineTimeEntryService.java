package redmine.rest.api.service.timeentry;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import redmine.rest.api.exception.IssueNotFoundException;
import redmine.rest.api.exception.UserNotFoundException;
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

    private final String url;
    private final IssueService issueService;
    private final UserService userService;
    private final ActivityService activityService;

    private final RestTemplate restTemplate;
    private final FailedPostWriterToFile writter;


    public RedmineTimeEntryService(RestTemplate restTemplate,
                                   IssueService issueService,
                                   UserService userService,
                                   ActivityService activityService) {
        this.restTemplate = restTemplate;
        this.issueService = issueService;
        this.userService = userService;
        this.activityService = activityService;
        this.url = "/time_entries.json";
        writter = new FailedPostWriterToFile();
    }

    @Override
    public TimeEntryData getTimeEntries() {
        return restTemplate.getForObject(url, TimeEntryData.class);
    }

    @Override
    public Optional<TimeEntry> postJiraWorkLog(JiraWorkLog jiraWorkLog) {
        try {
            PostTimeEntry timeEntry = createTimeEntry(jiraWorkLog);
            Optional<TimeEntry> returnOptional = Optional.ofNullable(
                    restTemplate.postForObject(url, timeEntry, TimeEntry.class));
            log.info("Posted work log");
            return returnOptional;
        } catch (Exception e) {
            log.error(e.getMessage());
            writter.logWrongJSONToFile(new FailedPost(jiraWorkLog, e.getMessage()));
            return Optional.empty();
        }
    }


    @Override
    public List<TimeEntry> postJiraWorkLogs(JiraPackage jiraPackage) {
        int count = 0;
        ArrayList<TimeEntry> postedEntries = new ArrayList<>();
        ArrayList<FailedPost> failedPosts = new ArrayList<>();
        for (JiraWorkLog workLog : jiraPackage.getWorkLogs()) {
            try {
                PostTimeEntry timeEntry = createTimeEntry(workLog);
                postedEntries.add(restTemplate.postForObject(url, timeEntry, TimeEntry.class));
                count++;
            } catch (Exception e) {
                log.error(e.getMessage());
                failedPosts.add(new FailedPost(workLog, e.getMessage()));
            }
        }
        if (!failedPosts.isEmpty()) {
            writter.logWrongJSONToFile(failedPosts);
        }
        log.info("Posted " + count + " work logs, out of " + jiraPackage.getWorkLogs().size());
        return postedEntries;
    }

    private PostTimeEntry createTimeEntry(JiraWorkLog jiraWorkLog) throws IssueNotFoundException, UserNotFoundException {
        Optional<Long> issueId = issueService.getIssueIdFromName(jiraWorkLog.getIssueId());
        Optional<Long> userId = userService.findUserIdByName(jiraWorkLog.getAccountId());

        return PostTimeEntry.builder()
                .issueId(issueId.orElseThrow(IssueNotFoundException::new))
                .userId(userId.orElseThrow(UserNotFoundException::new))
                .hours(secondsToHoursConverter(jiraWorkLog.getTimeSpentSeconds()))
                .comments(jiraWorkLog.getDescription())
                .activityId(activityService.findActivityFromName(jiraWorkLog.getDescription()))
                .spentOn(jiraWorkLog.getStartDate())
                .build();
    }

    private double secondsToHoursConverter(int seconds) {
        return (double) seconds / SECONDS_IN_MINUTE / MINUTES_IN_HOUR;
    }
}
