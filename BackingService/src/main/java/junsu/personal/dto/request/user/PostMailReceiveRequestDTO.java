package junsu.personal.dto.request.user;

import jakarta.validation.constraints.NotBlank;

public record PostMailReceiveRequestDTO(
        @NotBlank
        String email,
        @NotBlank
        String code,
        @NotBlank
        String userType
) {

}
