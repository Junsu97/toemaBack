package junsu.personal.dto.request.teacher;

import lombok.Builder;


public record PatchApplyRequestDTO(
        String studentId,
        String teacherId,
        String content,
        String status,
        String userType
) {
}
