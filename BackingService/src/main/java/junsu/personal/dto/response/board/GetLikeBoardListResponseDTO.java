package junsu.personal.dto.response.board;

import junsu.personal.common.ResponseCode;
import junsu.personal.common.ResponseMessage;
import junsu.personal.dto.object.BoardListDTO;
import junsu.personal.dto.response.ResponseDTO;
import junsu.personal.entity.BoardListViewEntity;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

@Getter
public class GetLikeBoardListResponseDTO extends ResponseDTO {
    private List<BoardListDTO> likeList;
    private GetLikeBoardListResponseDTO(List<BoardListViewEntity> boardListViewEntities){
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        List<BoardListDTO> userBoardList = new ArrayList<>();
        this.likeList = BoardListDTO.getList(boardListViewEntities);
    }

    public static ResponseEntity<GetLikeBoardListResponseDTO> success(List<BoardListViewEntity> boardListViewEntities){
        GetLikeBoardListResponseDTO result = new GetLikeBoardListResponseDTO(boardListViewEntities);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    public static ResponseEntity<ResponseDTO> noExistUser() {
        ResponseDTO result = new ResponseDTO(ResponseCode.NOT_EXISTED_USER, ResponseMessage.NOT_EXISTED_USER);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }
}
