package junsu.personal.dto.request.homework;

import jakarta.validation.constraints.NotBlank;

public record PostHomeworkRequestDTO(
        @NotBlank
        String studentId,
        @NotBlank
        String teacherId,
        @NotBlank
        String startDate,
        @NotBlank
        String endDate,
        @NotBlank
        String content
) {
}
