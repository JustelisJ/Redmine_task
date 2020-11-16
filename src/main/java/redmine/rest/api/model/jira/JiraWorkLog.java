package redmine.rest.api.model.jira;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Data
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class JiraWorkLog {

    private String accountId;
    private String description;
    private int timeSpentSeconds;
    private LocalDate startDate;
    private String issueId;
    private String projectKey;

    @JsonSetter("startDate")
    public void setStartDate(String startDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        this.startDate = LocalDate.parse(startDate, formatter);
    }

}
