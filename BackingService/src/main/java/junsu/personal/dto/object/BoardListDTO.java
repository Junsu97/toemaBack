package junsu.personal.dto.object;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public record BoardListDTO(
        int boardNumber,
        String title,
        String content,
        String boardTitleImage,
        int favoriteCount,
        int commnetCount,
        int viewCount,
        String writeDateTime,
        String writerNickname,
        String writerProfileImage
) {
}
