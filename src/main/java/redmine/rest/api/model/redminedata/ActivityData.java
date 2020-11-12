package redmine.rest.api.model.redminedata;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import redmine.rest.api.model.Activity;

import java.util.List;

@Data
public class ActivityData {

    @JsonProperty("time_entry_activities")
    private List<Activity> timeEntryActivities;

}
