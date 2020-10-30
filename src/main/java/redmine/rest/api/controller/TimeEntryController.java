package redmine.rest.api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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

}
