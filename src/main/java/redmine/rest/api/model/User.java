package redmine.rest.api.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    private Long id;
    private String login;
    private boolean admin;
    @JsonAlias({"name"})
    private String firstname;
    private String lastname;
    private String mail;
    private LocalDate createdOn;
    private LocalDate lastLoginOn;

}
