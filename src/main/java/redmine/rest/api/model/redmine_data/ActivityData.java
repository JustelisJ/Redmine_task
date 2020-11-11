package redmine.rest.api.model.redmine_data;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;
import redmine.rest.api.model.Activity;

import java.util.List;

@Data
public class ActivityData {

    @JsonAlias("time_entry_activities")
    private List<Activity> timeEntryActivities;

}
