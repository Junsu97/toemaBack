package junsu.personal.dto.object;

import junsu.personal.repository.resultSet.GetFavoriteListResultSet;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Builder
public record FavoriteListDTO(
        String userId,
        String nickname,
        String profileImage
) {
    // 모든 필드를 초기화하는 다른 생성자로 위임
    public FavoriteListDTO(GetFavoriteListResultSet resultSet){
        this(resultSet.getUserId(), resultSet.getNickname(), resultSet.getProfileImage());
    }

    // 다른 메서드들
    public static List<FavoriteListDTO> copyList(List<GetFavoriteListResultSet> resultSets){
        List<FavoriteListDTO> list = new ArrayList<>();
        for(GetFavoriteListResultSet resultSet : resultSets){
            FavoriteListDTO favoriteListDTO = new FavoriteListDTO(resultSet);
            list.add(favoriteListDTO);
        }

        return list;
    }
}
