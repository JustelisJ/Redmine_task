package redmine.rest.api.model.redmineData;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PostTimeEntry {

    private Long issue_id;
    private Long user_id;
    private double hours;
    private String comments;
    private Long activity_id;

}
