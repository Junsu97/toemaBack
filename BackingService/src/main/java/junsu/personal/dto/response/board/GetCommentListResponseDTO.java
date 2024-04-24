package junsu.personal.dto.response.board;

import junsu.personal.common.ResponseCode;
import junsu.personal.common.ResponseMessage;
import junsu.personal.dto.object.CommentListDTO;
import junsu.personal.dto.response.ResponseDTO;
import junsu.personal.repository.resultSet.GetCommentListResultSet;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Slf4j
@Getter
public class GetCommentListResponseDTO extends ResponseDTO {
    private List<CommentListDTO> commentList;
    private GetCommentListResponseDTO(List<GetCommentListResultSet> resultSets){
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.commentList = CommentListDTO.copyList(resultSets);
    }
    public static ResponseEntity<GetCommentListResponseDTO> success(List<GetCommentListResultSet> resultSets){
        GetCommentListResponseDTO result = new GetCommentListResponseDTO(resultSets);
        log.info("result : " + result.getCommentList());
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
    public static ResponseEntity<ResponseDTO> noExistBoard(){
        ResponseDTO result = new ResponseDTO(ResponseCode.NOT_EXISTED_BOARD, ResponseMessage.NOT_EXISTED_BOARD);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }
}
