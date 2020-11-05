package redmine.rest.api.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
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
    private Date start_date;
    private Date due_date;
    private int done_ratio;
    @JsonAlias("is_private")
    private boolean is_private;
    private double estimated_hours;
    private Date created_on;
    private Date updated_on;
    private Date closed_on;

}
