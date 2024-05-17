package junsu.personal.dto.request.teacher;


import jakarta.validation.constraints.NotBlank;
import lombok.NonNull;

public record PostApplyTeacherRequestDTO(
    @NotBlank
    String teacherId,
    @NonNull
    String content
) {
}
