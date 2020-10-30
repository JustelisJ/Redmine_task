package redmine.rest.api.model.redmineData;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import redmine.rest.api.model.TimeEntry;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class TimeEntityData extends Metadata {

    private Set<TimeEntry> time_entries = new HashSet<>();

}
