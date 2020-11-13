package redmine.rest.api.model.redminedata;

import lombok.Data;
import redmine.rest.api.model.Activity;

import java.util.List;

@Data
public class ActivityData {

    private List<Activity> timeEntryActivities;

}
