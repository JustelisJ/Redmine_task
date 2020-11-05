package redmine.rest.api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import redmine.rest.api.model.jira.JiraPackage;
import redmine.rest.api.model.jira.JiraWorkLog;
import redmine.rest.api.service.timeEntry.TimeEntryService;

@Controller
public class TimeEntryController {

    private final TimeEntryService timeEntryService;

    public TimeEntryController(TimeEntryService timeEntryService) {
        this.timeEntryService = timeEntryService;
    }

    @GetMapping("/timeEntries")
    public String getIssues(Model model) {
        model.addAttribute("timeEntries", timeEntryService.getTimeEntries());
        return "timeEntry/allTimeEntries";
    }

    @PostMapping("/timeEntries/new")
    public String createTimeEntryFromJSON(Model model, @RequestBody JiraPackage jiraPackage) {

        //Saves the received JSON
        for (JiraWorkLog jiraWorkLog : jiraPackage.getWorkLogs()) {
            timeEntryService.postJiraWorkLog(jiraWorkLog);
        }

        model.addAttribute("timeEntries", timeEntryService.getTimeEntries());
        return "timeEntry/allTimeEntries";
    }

}
