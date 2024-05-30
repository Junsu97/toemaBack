package junsu.personal.dto.object;

import junsu.personal.repository.resultSet.GetCommentListResultSet;
import lombok.Builder;

import java.util.ArrayList;
import java.util.List;

@Builder
public record CommentListDTO(
        Integer commentNumber,
        Integer boardNumber,
        String nickname,
        String profileImage,
        String writeDatetime,
        String content
) {
    public CommentListDTO(GetCommentListResultSet resultSet){
        this(resultSet.getCommentNumber(), resultSet.getBoardNumber(), resultSet.getNickname(), resultSet.getProfileImage(), resultSet.getWriteDatetime(), resultSet.getContent());
    }
    public static List<CommentListDTO> copyList(List<GetCommentListResultSet> resultSets){
        List<CommentListDTO> list = new ArrayList<>();
        for(GetCommentListResultSet resultSet : resultSets){
            CommentListDTO commentListDTO = new CommentListDTO(resultSet);
            list.add(commentListDTO);
        }
        return list;
    }
}
