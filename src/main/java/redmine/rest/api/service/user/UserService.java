package redmine.rest.api.service.user;

import java.util.Optional;

public interface UserService {

    Optional<Long> findUserIdByName(String name);

}
