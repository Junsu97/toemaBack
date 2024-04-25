package junsu.personal.dto.response.board;

import junsu.personal.common.ResponseCode;
import junsu.personal.common.ResponseMessage;
import junsu.personal.dto.object.BoardListDTO;
import junsu.personal.dto.response.ResponseDTO;
import junsu.personal.entity.BoardListViewEntity;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Getter
public class GetSearchBoardListResponseDTO extends ResponseDTO {
    private List<BoardListDTO> searchList;

    private GetSearchBoardListResponseDTO(List<BoardListViewEntity> boardListViewEntities){
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.searchList = BoardListDTO.getList(boardListViewEntities);
    }

    public static ResponseEntity<GetSearchBoardListResponseDTO> success(List<BoardListViewEntity> boardListViewEntities){
        GetSearchBoardListResponseDTO result = new GetSearchBoardListResponseDTO(boardListViewEntities);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
