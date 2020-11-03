package redmine.rest.api.model;

import lombok.Data;

import java.util.Date;

@Data
public class TimeEntry {

    private Long id;
    private Project project;
    private User user;
    private Activity activity;
    private double hours;
    private String comments;
    private Date spent_on;
    private Date created_on;
    private Date updated_on;

}
