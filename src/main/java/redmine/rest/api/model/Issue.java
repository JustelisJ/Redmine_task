package redmine.rest.api.model;

import com.fasterxml.jackson.annotation.JsonAlias;
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
    @JsonAlias("start_date")
    private Date startDate;
    @JsonAlias("due_date")
    private Date dueDate;
    @JsonAlias("done_ratio")
    private int doneRatio;
    @JsonAlias("is_private")
    private boolean isPrivate;
    @JsonAlias("estimated_hours")
    private double estimatedHours;
    @JsonAlias("created_on")
    private Date createdOn;
    @JsonAlias("updated_on")
    private Date updatedOn;
    @JsonAlias("closed_on")
    private Date closedOn;

}
