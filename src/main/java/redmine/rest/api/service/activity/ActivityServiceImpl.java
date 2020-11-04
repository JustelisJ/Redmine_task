package redmine.rest.api.service.activity;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import redmine.rest.api.model.Activity;
import redmine.rest.api.model.redmineData.ActivityData;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class ActivityServiceImpl implements ActivityService {

    private final String url;
    private RestTemplate restTemplate;
    private Map<String, Long> activities;

    public ActivityServiceImpl(RestTemplate restTemplate,
                               @Value("${redmine.url}") String url) {
        this.restTemplate = restTemplate;
        this.url = url + "/enumerations/time_entry_activities.json";
        activities = new HashMap<>();
    }

    @Override
    public ActivityData getActivities() {
        return restTemplate.getForObject(url, ActivityData.class);
    }

    @Override
    public Long findActivityFromName(String name) {
        Long id = activities.get(name);
        if (id == null) {
            ActivityData activityData = getActivities();
            for (Activity activity : activityData.getTime_entry_activities()) {
                activities.put(activity.getName(), activity.getId());
                if (activity.is_default()) {      //TODO: Why u no get is_default field???
                    activities.put("default", activity.getId());
                }
            }
            id = activities.get(name);
            if (Objects.nonNull(id)) {
                //return activities.get("default");
                return 5L;
            } else {
                return id;
            }
        } else {
            return id;
        }
    }
}
