package redmine.rest.api.model.redminedata;

import lombok.Data;
import redmine.rest.api.model.User;

import java.util.List;

@Data
public class UserData extends Metadata {

    private List<User> users;

}
