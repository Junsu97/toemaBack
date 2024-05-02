package junsu.personal.dto.request.user;

import jakarta.validation.constraints.NotBlank;

public record PostMailSendRequestDTO(
        @NotBlank
        String email
) {

}
