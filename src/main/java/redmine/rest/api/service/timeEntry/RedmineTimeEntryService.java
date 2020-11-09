package redmine.rest.api.service.timeEntry;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import redmine.rest.api.model.TimeEntry;
import redmine.rest.api.model.jira.JiraPackage;
import redmine.rest.api.model.jira.JiraWorkLog;
import redmine.rest.api.model.redmineData.PostTimeEntry;
import redmine.rest.api.model.redmineData.TimeEntryData;
import redmine.rest.api.service.activity.ActivityService;
import redmine.rest.api.service.issue.IssueService;
import redmine.rest.api.service.user.UserService;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

@Log4j2
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
    public Optional<TimeEntry> postJiraWorkLog(JiraWorkLog jiraWorkLog) {
        PostTimeEntry timeEntry;
        try {
            timeEntry = PostTimeEntry.builder()
                    .issue_id(issueService.getIssueIdFromName(jiraWorkLog.getIssue().getKey()).get())
                    .user_id(userService.findUserIdByName(jiraWorkLog.getAuthor().getDisplayName()).get())
                    .hours(secondsToHoursConverter(jiraWorkLog.getTimeSpentSeconds()))
                    .comments(jiraWorkLog.getDescription())
                    .activity_id(activityService.findActivityFromName(jiraWorkLog.getDescription()).get())
                    .spent_on(jiraWorkLog.getStartDate())
                    .build();
            return Optional.of(restTemplate.postForObject(url, timeEntry, TimeEntry.class));
        } catch (Exception e) {
            logWrongJSONToFile(jiraWorkLog, e);
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
                PostTimeEntry timeEntry = PostTimeEntry.builder()
                        .issue_id(issueService.getIssueIdFromName(workLog.getIssue().getKey()).get())
                        .user_id(userService.findUserIdByName(workLog.getAuthor().getDisplayName()).get())
                        .hours(secondsToHoursConverter(workLog.getTimeSpentSeconds()))
                        .comments(workLog.getDescription())
                        .activity_id(activityService.findActivityFromName(workLog.getDescription()).get())
                        .spent_on(workLog.getStartDate())
                        .build();
                postedEntries.add(restTemplate.postForObject(url, timeEntry, TimeEntry.class));
            } catch (Exception e) {
                failedPosts.add(workLog);
                postMessages.add(e);
            }
        }
        if (!failedPosts.isEmpty()) {
            logWrongJSONToFile(failedPosts, postMessages);
        }
        return Optional.of(postedEntries);
    }

    private void logWrongJSONToFile(JiraWorkLog workLog, Exception e) {
        File incorrectJSONFile = new File(getResourcesAbsolutePath());
        try {
            if (incorrectJSONFile.createNewFile()) {
                initializeIncorrectJSONFile(incorrectJSONFile);
                log.info("Incorrent JSON object file is created");
            }
            FileWriter writer = new FileWriter(incorrectJSONFile, true);
            String json = createWorkLogJSONWithErrorParameter(workLog, e);
            writer.write(json + "\n]");
            writer.flush();
            writer.close();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    private void logWrongJSONToFile(ArrayList<JiraWorkLog> workLogs, ArrayList<Exception> e) {
        File incorrectJSONFile = new File(getResourcesAbsolutePath());
        try {
            if (incorrectJSONFile.createNewFile()) {
                initializeIncorrectJSONFile(incorrectJSONFile);
                log.info("Incorrent JSON object file is created");
            }
            FileWriter writer = new FileWriter(incorrectJSONFile, true);
            for (int i = 0; i < workLogs.size(); i++) {
                String json = createWorkLogJSONWithErrorParameter(workLogs.get(i), e.get(i));
                if (i != workLogs.size() - 1) {
                    writer.write(json + ",\n");
                } else {
                    writer.write(json + "\n]\n}");
                }
                writer.flush();
            }
            writer.close();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    private String getResourcesAbsolutePath() {
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date());
        File currentDirFile = new File("");
        String helper = currentDirFile.getAbsolutePath();
        String currentDir = helper + "/src/main/resources/json/incorrect_" + timeStamp + ".json";
        return currentDir;
    }

    private void initializeIncorrectJSONFile(File file) {
        try {
            FileWriter writer = new FileWriter(file);
            writer.write("{\n\"results\": [\n");
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String createWorkLogJSONWithErrorParameter(JiraWorkLog log, Exception e) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(log);
        String exceptionJSON = ",\"exception\":\"" + e.getMessage() + "\"";
        return new StringBuilder(json).insert(json.length() - 1, exceptionJSON).toString();
    }

    private double secondsToHoursConverter(int seconds) {
        return (double) seconds / SECONDS_IN_MINUTE / MINUTES_IN_HOUR;
    }
}
