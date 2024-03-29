package junsu.personal.dto.object;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public record CommentListDTO(
        String nickName,
        String profileImage,
        String writeDateTime,
        String content
) {
}
