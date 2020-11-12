package redmine.rest.api.model.redminedata;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
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

    @JsonAlias("issue_id")
    private Long issueId;
    @JsonAlias("user_id")
    private Long userId;
    private double hours;
    private String comments;
    @JsonAlias("activity_id")
    private Long activityId;
    @JsonAlias("spent_on")
    private Date spentOn;

    @JsonProperty("issue_id")
    public Long getIssueId() {
        return issueId;
    }

}
