package redmine.rest.api.model.redminedata;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonRootName("time_entry")
public class PostTimeEntry {

    private Long issueId;
    private Long userId;
    private double hours;
    private String comments;
    private Long activityId;
    private Date spentOn;

}
