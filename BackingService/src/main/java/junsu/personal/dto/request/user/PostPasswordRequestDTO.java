package junsu.personal.dto.request.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record PostPasswordRequestDTO(
        @NotBlank
        String userId,
        @NotBlank
        String userName,
        @NotBlank
        String email,
        @NotBlank
        String userType
) {
}
