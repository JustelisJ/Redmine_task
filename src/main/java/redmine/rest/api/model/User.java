package redmine.rest.api.model;

import lombok.Data;

import java.util.Date;

@Data
public class User {

    private Long id;
    private String login;
    private boolean admin;
    private String firstname;
    private String lastname;
    private String mail;
    private Date created_on;
    private Date last_login_on;
    private String name;

}
