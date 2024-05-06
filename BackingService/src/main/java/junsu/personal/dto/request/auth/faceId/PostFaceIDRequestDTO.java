package junsu.personal.dto.request.auth.faceId;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import junsu.personal.dto.request.auth.faceId.object.Expressions;
import junsu.personal.dto.request.auth.faceId.object.LandMark;

public record PostFaceIDRequestDTO(
        @NotBlank
        String userId,
        @NotNull
        @DecimalMin("0.0")
        @DecimalMax("1.0")
        double accuracy,
        @NotNull
        LandMark landMarks
) {
}
