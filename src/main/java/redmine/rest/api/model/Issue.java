package redmine.rest.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

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
    private Date startDate;
    private Date dueDate;
    private int doneRatio;
    private boolean isPrivate;
    private double estimatedHours;
    private Date createdOn;
    private Date updatedOn;
    private Date closedOn;

}
