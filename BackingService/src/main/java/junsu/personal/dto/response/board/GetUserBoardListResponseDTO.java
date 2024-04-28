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
public class GetUserBoardListResponseDTO extends ResponseDTO {
    private List<BoardListDTO> userBoardList;

    private GetUserBoardListResponseDTO(List<BoardListViewEntity> boardListViewEntities){
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        List<BoardListDTO> userBoardList = new ArrayList<>();
        this.userBoardList = BoardListDTO.getList(boardListViewEntities);
    }

    public static ResponseEntity<GetUserBoardListResponseDTO> success(List<BoardListViewEntity> boardListViewEntities){
        GetUserBoardListResponseDTO result = new GetUserBoardListResponseDTO(boardListViewEntities);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    public static ResponseEntity<ResponseDTO> noExistUser() {
        ResponseDTO result = new ResponseDTO(ResponseCode.NOT_EXISTED_USER, ResponseMessage.NOT_EXISTED_USER);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }

}
