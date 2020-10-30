package redmine.rest.api.model.redmineData;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public abstract class Metadata {

    private int total_count;
    private int offset;
    private int limit;

}
