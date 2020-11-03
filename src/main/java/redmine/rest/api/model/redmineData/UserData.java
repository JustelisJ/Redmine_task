package redmine.rest.api.model.redmineData;

import lombok.Data;
import redmine.rest.api.model.User;

import java.util.Set;

@Data
public class UserData extends Metadata {

    private Set<User> users;

}
