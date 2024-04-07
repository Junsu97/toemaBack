package junsu.personal.dto.object;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
public record CommentListDTO(
        String nickName,
        String profileImage,
        String writeDateTime,
        String content
) {
}
