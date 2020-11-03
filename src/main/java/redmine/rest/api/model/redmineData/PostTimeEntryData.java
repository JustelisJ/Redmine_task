package redmine.rest.api.model.redmineData;

import lombok.*;

@Data
@AllArgsConstructor
@Builder
public class PostTimeEntryData {

    private Long issue_id;
    private Long user_id;
    private String hours;
    private String comments;
    private Long activity_id;

}
