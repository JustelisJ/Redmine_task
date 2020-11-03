package redmine.rest.api.model.redmineData;

import lombok.Data;
import redmine.rest.api.model.TimeEntry;

import java.util.Set;

@Data
public class TimeEntryData extends Metadata {

    private Set<TimeEntry> time_entries;

}
