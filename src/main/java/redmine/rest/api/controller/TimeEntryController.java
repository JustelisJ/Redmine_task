package redmine.rest.api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import redmine.rest.api.model.TimeEntry;
import redmine.rest.api.model.jira.JiraPackage;
import redmine.rest.api.model.jira.JiraResult;
import redmine.rest.api.service.timeEntry.TimeEntryService;

@Controller
public class TimeEntryController {

    private final TimeEntryService timeEntryService;

    public TimeEntryController(TimeEntryService timeEntryService) {
        this.timeEntryService = timeEntryService;
    }

    @GetMapping("/timeEntries")
    public String getIssues(Model model){
        model.addAttribute("timeEntries", timeEntryService.getTimeEntries());
        return "timeEntry/allTimeEntries";
    }

    @PostMapping("/timeEntries/new")
    public String createTimeEntryFromJSON(Model model, @RequestBody JiraPackage jiraPackage){
        System.out.println(jiraPackage);

        //Saves the received JSON
        for (JiraResult jiraResult:jiraPackage.getResults()) {
            timeEntryService.postTimeEntry(jiraResult);
        }

        model.addAttribute("timeEntries", timeEntryService.getTimeEntries());
        return "timeEntry/allTimeEntries";
    }

}