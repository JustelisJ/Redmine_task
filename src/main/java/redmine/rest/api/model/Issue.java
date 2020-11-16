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
public class Issue {

    private Long id;
    private Project project;
    private Tracker tracker;
    private Status status;
    private Priority priority;
    private User author;
    private String subject;
    private String description;
    private LocalDate startDate;
    private LocalDate dueDate;
    private int doneRatio;
    private boolean isPrivate;
    private double estimatedHours;
    private LocalDate createdOn;
    private LocalDate updatedOn;
    private LocalDate closedOn;

}
