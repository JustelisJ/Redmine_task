package redmine.rest.api.model.redmineData;

import lombok.Data;

@Data
public abstract class Metadata {

    private int total_count;
    private int offset;
    private int limit;

}
