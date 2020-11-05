package redmine.rest.api.service.activity;

import java.util.Optional;

public interface ActivityService {

    Optional<Long> findActivityFromName(String name);

}
