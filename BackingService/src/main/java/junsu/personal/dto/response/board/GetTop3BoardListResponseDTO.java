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
public class GetTop3BoardListResponseDTO extends ResponseDTO {
    private List<BoardListDTO> top3List;

    private GetTop3BoardListResponseDTO(List<BoardListViewEntity> boardListViewEntities){
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.top3List = BoardListDTO.getList(boardListViewEntities);
    }

    public static ResponseEntity<GetTop3BoardListResponseDTO> success(List<BoardListViewEntity> boardListViewEntities){
        GetTop3BoardListResponseDTO result = new GetTop3BoardListResponseDTO(boardListViewEntities);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
