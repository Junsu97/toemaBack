package junsu.personal.dto.object;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public record DataObject(
        String baseYear,
        String sido,
        String grade,
        Long participationRate
) {
    @JsonCreator
    public DataObject(
            @JsonProperty("baseYear") String baseYear,
            @JsonProperty("sido") String sido,
            @JsonProperty("grade") String grade,
            @JsonProperty("participationRate") Long participationRate) {
        this.baseYear = baseYear;
        this.sido = sido;
        this.grade = grade;
        this.participationRate = participationRate;
    }
}
