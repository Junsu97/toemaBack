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
public class GetLatestBoardListResponseDTO extends ResponseDTO {
    private List<BoardListDTO> latestList;
    private GetLatestBoardListResponseDTO(List<BoardListViewEntity> boardEntities){
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.latestList = BoardListDTO.getList(boardEntities);
    }

    public static ResponseEntity<GetLatestBoardListResponseDTO> success(List<BoardListViewEntity> boardEntities){
        GetLatestBoardListResponseDTO result = new GetLatestBoardListResponseDTO(boardEntities);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }


}
