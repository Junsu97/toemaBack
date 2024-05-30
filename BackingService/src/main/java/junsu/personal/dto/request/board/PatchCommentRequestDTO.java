package junsu.personal.dto.request.board;

import jakarta.validation.constraints.NotBlank;

public record PatchCommentRequestDTO(
        @NotBlank
        Long boardNumber,
        @NotBlank
        Long commentNumber,
        @NotBlank
        String content
) {
}
