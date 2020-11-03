package redmine.rest.api.model;

import lombok.Data;

import java.util.Date;

@Data
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
    private boolean is_private;
    private double estimated_hours;
    private Date created_on;
    private Date updated_on;
    private Date closed_on;

}
