package redmine.rest.api.service.activity;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import redmine.rest.api.model.Activity;
import redmine.rest.api.model.redmineData.ActivityData;

import java.util.HashMap;
import java.util.Map;

@Service
public class ActivityServiceImpl implements ActivityService {

    private RestTemplate restTemplate;
    private final String url;
    private Map<String, Long> activities;

    public ActivityServiceImpl(RestTemplate restTemplate,
                               @Value("${redmine.url}") String url) {
        this.restTemplate = restTemplate;
        this.url = url+"/enumerations/time_entry_activities.json";
        activities = new HashMap<>();
    }

    @Override
    public ActivityData getActivities() {
        return restTemplate.getForObject(url, ActivityData.class);
    }

    @Override
    public Long findActivityFromName(String name) {
        try{
            return activities.get(name);
        }
        catch (Exception e){
            ActivityData activityData = getActivities();
            for (Activity activity:activityData.getTime_entry_activities()) {
                activities.put(activity.getName(), activity.getId());
            }
            try {
                return activities.get(name);
            }
            catch (Exception e1){
                throw new RuntimeException("No such activity exists");
            }
        }
    }
}
