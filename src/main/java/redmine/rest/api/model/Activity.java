package redmine.rest.api.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Activity {

    private Long id;
    private String name;
    @JsonAlias({"is_default"})
    private boolean is_default;
    private boolean active;

}
