package redmine.rest.api.model.redmineData;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties({"total_count"})
public class Metadata {

    private int offset;
    private int limit;

}
