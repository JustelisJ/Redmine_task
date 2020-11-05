package redmine.rest.api.service.activity;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import redmine.rest.api.model.Activity;
import redmine.rest.api.model.redmineData.ActivityData;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Log4j2
@Service
public class RedmineActivityService implements ActivityService {

    private final String url;
    private RestTemplate restTemplate;
    private Map<String, Long> activities;
    private final Long DEFAULT_VALUE_TEMP = 5L;

    public RedmineActivityService(RestTemplate restTemplate,
                                  @Value("${redmine.url}") String url) {
        this.restTemplate = restTemplate;
        this.url = url + "/enumerations/time_entry_activities.json";
        activities = new HashMap<>();
        mapAllActivities();
    }

    @Override
    public Optional<Long> findActivityFromName(String name) {
        Long id = activities.getOrDefault(name, DEFAULT_VALUE_TEMP);
        return Optional.of(id);
    }

    private ActivityData getActivities() {
        return restTemplate.getForObject(url, ActivityData.class);
    }

    @Scheduled(fixedRate = 10000)
    private void mapAllActivities() {
        ActivityData activityData = getActivities();
        for (Activity activity : activityData.getTime_entry_activities()) {
            activities.put(activity.getName(), activity.getId());
            if (activity.is_default()) {      //TODO: Why u no get is_default field???
                activities.put("default", activity.getId());
            }
        }
        log.info("Mapped all activities");
    }
}
