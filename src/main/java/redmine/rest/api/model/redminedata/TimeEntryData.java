package redmine.rest.api.model.redminedata;

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

    private List<TimeEntry> timeEntries;

}
