package junsu.personal.dto.request.tutoring;

import jakarta.validation.constraints.NotBlank;

public record PostTutoringRequestDTO(
        @NotBlank
        String studentId,
        @NotBlank
        String teacherId,
        @NotBlank
        String tutoringStartTime,
        @NotBlank
        String tutoringEndTime,
        @NotBlank
        String tutoringDate,
        @NotBlank
        String tutoringSubject
) {
}
