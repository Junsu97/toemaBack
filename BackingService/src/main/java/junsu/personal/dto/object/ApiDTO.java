package junsu.personal.dto.object;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record ApiDTO(
    DataObject[] data
) {
}
