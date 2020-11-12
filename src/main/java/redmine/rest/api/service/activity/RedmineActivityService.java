package redmine.rest.api.service.activity;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import redmine.rest.api.model.Activity;
import redmine.rest.api.model.redminedata.ActivityData;

import java.util.HashMap;
import java.util.Map;

@Log4j2
@Service
public class RedmineActivityService implements ActivityService {

    private static final String COULDNT_MAP_MESSAGE = "Couldn't map activities";
    private static final String MAPPED_ACTIVITIES_MESSAGE = "Mapped all activities";

    private final String url;
    private final RestTemplate restTemplate;
    private Map<String, Long> activities;

    public RedmineActivityService(RestTemplate restTemplate,
                                  @Value("${redmine.url}") String url) {
        this.restTemplate = restTemplate;
        this.url = url + "/enumerations/time_entry_activities.json";
        activities = new HashMap<>();
        mapAllActivities();
    }

    @Override
    public Long findActivityFromName(String name) {
        return activities.getOrDefault(name, activities.get("default"));
    }

    private ActivityData getActivities() {
        return restTemplate.getForObject(url, ActivityData.class);
    }

    @Scheduled(fixedRate = 300000)
    private void mapAllActivities() {
        ActivityData activityData = getActivities();
        if (activityData != null) {
            for (Activity activity : activityData.getTimeEntryActivities()) {
                activities.put(activity.getName(), activity.getId());
                if (activity.isDefault()) {
                    activities.put("default", activity.getId());
                }
            }
            log.info(MAPPED_ACTIVITIES_MESSAGE);
        } else {
            log.error(COULDNT_MAP_MESSAGE);
        }
    }
}
