package redmine.rest.api.service.user;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import redmine.rest.api.exception.NoUserFoundException;
import redmine.rest.api.model.User;
import redmine.rest.api.model.redmineData.UserData;

import java.util.HashMap;
import java.util.Map;

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
        mappAllUsers();
    }

    @Override
    public UserData getUsers() {
        return restTemplate.getForObject(url, UserData.class);
    }

    @Override
    public Long findUserIdByName(String name) {
        Long id = users.get(name);
        if (id == null) {
            mappAllUsers();
            id = users.get(name);
            if (id == null) {
                throw new NoUserFoundException();
            } else {
                return id;
            }
        } else {
            return id;
        }
    }

    private void mappAllUsers() {
        UserData userData = getUsers();
        for (User user : userData.getUsers()) {
            users.put(user.getFirstname() + " " + user.getLastname(), user.getId());
        }
    }
}
