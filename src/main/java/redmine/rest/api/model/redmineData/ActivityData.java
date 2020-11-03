package redmine.rest.api.model.redmineData;

import lombok.Data;
import redmine.rest.api.model.Activity;

import java.util.Set;

@Data
public class ActivityData {

    private Set<Activity> time_entry_activities;

}
