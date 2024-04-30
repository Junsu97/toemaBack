package junsu.personal.dto.request.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record PatchAndPostPasswordRequestDTO(
        @NotBlank
        String password,
        @NotBlank
        String userType
) {
}
