package redmine.rest.api.model.redmineData;

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

    private Long issue_id;
    private Long user_id;
    private double hours;
    private String comments;
    private Long activity_id;
    private Date spent_on;

}
