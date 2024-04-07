package junsu.personal.dto.object;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
public record FavoriteListDTO(
        String userId,
        String nickName,
        String profileImage
) {
}
