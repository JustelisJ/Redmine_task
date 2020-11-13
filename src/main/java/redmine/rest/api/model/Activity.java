package redmine.rest.api.model;

import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Activity {

    private Long id;
    private String name;
    private boolean isDefault;
    private boolean active;

    @JsonSetter("is_default")
    public void setIsDefault(boolean isDefault) {
        this.isDefault = isDefault;
    }

}
