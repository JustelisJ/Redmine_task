package redmine.rest.api.service.activity;

import redmine.rest.api.model.redmineData.ActivityData;

public interface ActivityService {

    ActivityData getActivities();

    Long findActivityFromName(String name);

}
