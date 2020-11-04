package redmine.rest.api.service.user;

import redmine.rest.api.model.redmineData.UserData;

public interface UserService {

    UserData getUsers();

    Long findUserIdByName(String name);

}
