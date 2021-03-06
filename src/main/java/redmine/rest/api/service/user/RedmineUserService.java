package redmine.rest.api.service.user;

import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import redmine.rest.api.model.User;
import redmine.rest.api.model.redminedata.UserData;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Log4j2
@Service
public class RedmineUserService implements UserService {

    private static final String MAPPED_USERS_MESSAGE = "Mapped all users";
    private static final String COULDNT_MAP_MESSAGE = "Couldn't map users";

    private final String url;
    private final RestTemplate restTemplate;
    private Map<String, Long> users;

    public RedmineUserService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        this.url = "/users.json";
        users = new HashMap<>();

        //temp---
        users.put("5f9a8e6762584c006be3447c", 5L);
        //------

        mapAllUsers();
    }

    @Override
    public Optional<Long> findUserIdByName(String name) {
        return Optional.ofNullable(users.get(name));
    }

    private UserData getUsers() {
        return restTemplate.getForObject(url, UserData.class);
    }

    @Scheduled(fixedRate = 300000)
    private void mapAllUsers() {
        UserData userData = getUsers();
        if (userData != null) {
            for (User user : userData.getUsers()) {
                users.put(getFirstnameAndLastname(user), user.getId());
            }
            log.info(MAPPED_USERS_MESSAGE);
        } else {
            log.warn(COULDNT_MAP_MESSAGE);
        }
    }

    private String getFirstnameAndLastname(User user) {
        return user.getFirstname() + " " + user.getLastname();
    }
}
