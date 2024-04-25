package junsu.personal.dto.object;

import junsu.personal.entity.BoardListViewEntity;
import lombok.Builder;

import java.util.ArrayList;
import java.util.List;

@Builder
public record BoardListDTO(
        Long boardNumber,
        String title,
        String content,
        String boardTitleImage,
        Long favoriteCount,
        Long commentCount,
        Long viewCount,
        String writeDateTime,
        String writerNickname,
        String writerProfileImage
) {
    public BoardListDTO(BoardListViewEntity boardListViewEntity) {
        this(boardListViewEntity.getBoardNumber(), boardListViewEntity.getTitle(),
                boardListViewEntity.getContent(), boardListViewEntity.getTitleImage(),
                boardListViewEntity.getFavoriteCount(), boardListViewEntity.getCommentCount(),
                boardListViewEntity.getViewCount(), boardListViewEntity.getWriteDatetime(),
                boardListViewEntity.getWriterNickname(),
                boardListViewEntity.getWriterProfileImage()
        );
    }

    public static List<BoardListDTO> getList(List<BoardListViewEntity> boardListViewEntities){
        List<BoardListDTO> list = new ArrayList<>();
        for(BoardListViewEntity boardListViewEntity : boardListViewEntities){
            BoardListDTO boardListDTO = new BoardListDTO(boardListViewEntity);
            list.add(boardListDTO);
        }
        return list;
    }
}
