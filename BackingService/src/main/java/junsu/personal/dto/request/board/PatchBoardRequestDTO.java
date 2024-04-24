package junsu.personal.dto.request.board;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.List;

@Builder
public record PatchBoardRequestDTO(
        @NotBlank
        String title,
        @NotBlank
        String content,
        @NotNull
        List<String> boardImageList
) {
}
