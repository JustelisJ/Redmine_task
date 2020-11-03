package redmine.rest.api.service.user;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import redmine.rest.api.model.User;
import redmine.rest.api.model.redmineData.UserData;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    private RestTemplate restTemplate;
    private final String url;
    private Map<String, Long> users;

    public UserServiceImpl(RestTemplate restTemplate,
                           @Value("${redmine.url}") String url) {
        this.restTemplate = restTemplate;
        restTemplate.getInterceptors().add(new BasicAuthenticationInterceptor("logger", "asdasdasd"));
        this.url = url+"/users.json";
        users = new HashMap<>();
    }

    @Override
    public UserData getUsers() {
        return restTemplate.getForObject(url, UserData.class);
    }

    @Override
    public Long findUserIdByName(String name) {
        try{
            return users.get("name");
        }
        catch (Exception e){
            UserData allUsers = getUsers();
            for (User user:allUsers.getUsers()) {
                users.put(user.getFistName()+" "+user.getLastName(), user.getId());
            }
            try{
                return users.get(name);
            }
            catch (Exception e1){
                throw new RuntimeException("No such user exists");
            }
        }
    }
}
