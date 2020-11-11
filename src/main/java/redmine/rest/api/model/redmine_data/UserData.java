package redmine.rest.api.model.redmine_data;

import lombok.Data;
import redmine.rest.api.model.User;

import java.util.List;

@Data
public class UserData extends Metadata {

    private List<User> users;

}
