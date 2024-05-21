package junsu.personal.dto.request.teacher;

import jakarta.validation.constraints.NotBlank;

public record GetApplyListRequestDTO(
        @NotBlank
        String userId,
        @NotBlank
        String userType
) {

}
