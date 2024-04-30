package junsu.personal.dto.request.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record PatchNicknameRequestDTO(
        @NotBlank
        String nickname,
        @NotBlank
        String userType
) {
}
