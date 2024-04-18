package junsu.personal.dto.request.board;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record PostCommentRequestDTO(
        @NotBlank // int boolean 못씀
        String content
) {
}
