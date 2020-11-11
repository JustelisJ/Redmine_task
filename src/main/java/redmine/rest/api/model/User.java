package redmine.rest.api.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    private Long id;
    private String login;
    private boolean admin;
    @JsonAlias({"firstname", "name"})
    private String firstname;
    private String lastname;
    private String mail;
    @JsonAlias("created_on")
    private Date createdOn;
    @JsonAlias("last_login_on")
    private Date lastLoginOn;

}
