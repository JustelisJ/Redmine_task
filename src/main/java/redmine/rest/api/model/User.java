package redmine.rest.api.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class User {

    private Long id;
    private String login;
    private boolean admin;
    @JsonAlias({"firstname", "name"})
    private String firstname;
    private String lastname;
    private String mail;
    private Date created_on;
    private Date last_login_on;

}
