package redmine.rest.api.model;

import lombok.Data;

@Data
public class Activity {

    private Long id;
    private String name;
    private boolean is_default;
    private boolean active;

}
