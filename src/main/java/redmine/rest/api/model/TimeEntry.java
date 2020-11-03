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
