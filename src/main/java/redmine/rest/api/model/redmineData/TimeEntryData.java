package redmine.rest.api.model.redmineData;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import redmine.rest.api.model.TimeEntry;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TimeEntryData extends Metadata {

    private Set<TimeEntry> time_entries;

}
