package junsu.personal.dto.request.faceId.object;

import jakarta.validation.constraints.NotBlank;

public record Position(
        @NotBlank
        double x,
        @NotBlank
        double y
) {
}
