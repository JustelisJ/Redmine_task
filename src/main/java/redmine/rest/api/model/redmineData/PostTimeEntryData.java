package redmine.rest.api.model.redmineData;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonRootName("time_entry")
public class PostTimeEntryData {

    private Long issue_id;
    private Long user_id;
    private double hours;
    private String comments;
    private Long activity_id;

}
