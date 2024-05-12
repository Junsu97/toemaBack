package junsu.personal.dto.request.teacher;


import jakarta.validation.constraints.NotBlank;

public record PostApplyTeacherRequestDTO(
    @NotBlank
    String teacherId
) {
}
