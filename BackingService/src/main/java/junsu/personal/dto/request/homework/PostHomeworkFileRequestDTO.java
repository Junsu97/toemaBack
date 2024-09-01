package junsu.personal.dto.request.homework;

import jakarta.validation.constraints.NotBlank;

public record PostHomeworkFileRequestDTO(
        @NotBlank
        String url,
        @NotBlank
        String orgName,
        @NotBlank
        String ext,
        @NotBlank
        String regDt
) {
}
