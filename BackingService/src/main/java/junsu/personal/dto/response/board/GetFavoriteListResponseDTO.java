package junsu.personal.dto.response.board;

import junsu.personal.common.ResponseCode;
import junsu.personal.common.ResponseMessage;
import junsu.personal.dto.object.FavoriteListDTO;
import junsu.personal.dto.response.ResponseDTO;
import junsu.personal.repository.resultSet.GetFavoriteListResultSet;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Getter
public class GetFavoriteListResponseDTO extends ResponseDTO {
    private List<FavoriteListDTO> favoriteList;

    private GetFavoriteListResponseDTO(List<GetFavoriteListResultSet> resultSets){
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.favoriteList = FavoriteListDTO.copyList(resultSets);
    }

    public static ResponseEntity<GetFavoriteListResponseDTO> success(List<GetFavoriteListResultSet> resultSets){
        GetFavoriteListResponseDTO result = new GetFavoriteListResponseDTO(resultSets);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    public static ResponseEntity<ResponseDTO> noExistBoard(){
        ResponseDTO result = new ResponseDTO(ResponseCode.NOT_EXISTED_BOARD, ResponseMessage.NOT_EXISTED_BOARD);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }
}
