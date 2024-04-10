package junsu.personal.dto.request.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record SignInRequestDTO(
        @NotBlank
        String userId,
        @NotBlank
        String password,

        @NotBlank
        String userType
) {
}
