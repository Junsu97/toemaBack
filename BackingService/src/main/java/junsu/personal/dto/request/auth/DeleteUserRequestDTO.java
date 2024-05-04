package junsu.personal.dto.request.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record DeleteUserRequestDTO(
        @NotBlank
        String userType
) {
}
