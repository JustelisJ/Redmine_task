package redmine.rest.api.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties({"time_entry"})
public class TimeEntry {


    private Long id;
    private Project project;
    private Issue issue;
    private User user;
    private Activity activity;
    private double hours;
    private String comments;
    @JsonAlias("spent_on")
    private Date spentOn;
    @JsonAlias("created_on")
    private Date createdOn;
    @JsonAlias("updated_on")
    private Date updatedOn;

}
