package redmine.rest.api.service.user;

import redmine.rest.api.exception.NoUserFoundException;

import java.util.Optional;

public interface UserService {

    Optional<Long> findUserIdByName(String name) throws NoUserFoundException;

}
