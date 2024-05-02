package junsu.personal.dto.request.user;

import jakarta.validation.constraints.NotBlank;

public record PostCheckPasswordRequestDTO(
        @NotBlank
        String password,
        @NotBlank
        String userType
) {

}
