package redmine.rest.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TimeEntry {


    private Long id;
    private Project project;
    private Issue issue;
    private User user;
    private Activity activity;
    private double hours;
    private String comments;
    private LocalDate spentOn;
    private LocalDate createdOn;
    private LocalDate updatedOn;

}
