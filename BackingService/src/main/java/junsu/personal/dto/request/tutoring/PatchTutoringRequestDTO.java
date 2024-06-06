package junsu.personal.dto.request.tutoring;

import jakarta.validation.constraints.NotBlank;


public record PatchTutoringRequestDTO(
        @NotBlank
        String tutoringDate,
        @NotBlank
        String tutoringStartTime,
        @NotBlank
        String tutoringEndTime,
        @NotBlank
        String tutoringSubject
) {
}
