package junsu.personal.dto.request.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record PatchProfileImageRequestDTO(
        String profileImage,
        @NotBlank
        String userType
) {
}
