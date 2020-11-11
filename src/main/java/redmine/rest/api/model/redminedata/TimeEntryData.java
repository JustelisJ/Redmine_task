package redmine.rest.api.model.redminedata;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import redmine.rest.api.model.TimeEntry;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TimeEntryData extends Metadata {

    @JsonAlias("time_entries")
    private List<TimeEntry> timeEntries;

}
