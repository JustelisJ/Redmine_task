package redmine.rest.api.service.user;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import redmine.rest.api.exception.NoUserFoundException;
import redmine.rest.api.model.User;
import redmine.rest.api.model.redmineData.UserData;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Log4j2
@Service
public class RedmineUserService implements UserService {

    private final String url;
    private RestTemplate restTemplate;
    private Map<String, Long> users;

    public RedmineUserService(RestTemplate restTemplate,
                              @Value("${redmine.url}") String url) {
        this.restTemplate = restTemplate;
        this.url = url + "/users.json";
        users = new HashMap<>();
        mapAllUsers();
    }

    @Override
    public Optional<Long> findUserIdByName(String name) {
        Long id = users.get(name);
        if (id == null) {
            mapAllUsers();
            id = users.get(name);
            if (id == null) {
                log.error("No such user in redmine", new NoUserFoundException());
                throw new NoUserFoundException();
            } else {
                return Optional.of(id);
            }
        } else {
            return Optional.of(id);
        }
    }

    private UserData getUsers() {
        return restTemplate.getForObject(url, UserData.class);
    }

    private void mapAllUsers() {
        UserData userData = getUsers();
        for (User user : userData.getUsers()) {
            users.put(getFirstnameAndLastname(user), user.getId());
        }
    }

    private String getFirstnameAndLastname(User user) {
        return user.getFirstname() + " " + user.getLastname();
    }
}
