package junsu.personal.dto.response.tutoring;

import junsu.personal.common.ResponseCode;
import junsu.personal.common.ResponseMessage;
import junsu.personal.dto.object.HomeworkListDTO;
import junsu.personal.dto.object.TutoringListDTO;
import junsu.personal.dto.response.ResponseDTO;
import junsu.personal.entity.HomeworkEntity;
import junsu.personal.entity.TutoringEntity;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Getter
public class GetTutoringListResponseDTO extends ResponseDTO {
    List<TutoringListDTO> tutoringList;
    private GetTutoringListResponseDTO(List<TutoringEntity> entities){
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.tutoringList = TutoringListDTO.getList(entities);
    }
    public static ResponseEntity<GetTutoringListResponseDTO> success(List<TutoringEntity> entities){
        GetTutoringListResponseDTO result = new GetTutoringListResponseDTO(entities);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
    public static ResponseEntity<ResponseDTO> noExistTutoring(){
        ResponseDTO result = new ResponseDTO(ResponseCode.NOT_EXISTED_TUTORING, ResponseMessage.NOT_EXISTED_TUTORING);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }
    public static ResponseEntity<ResponseDTO> noExistMatch(){
        ResponseDTO result = new ResponseDTO(ResponseCode.NOT_EXISTED_MATCH, ResponseMessage.NOT_EXISTED_MATCH);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }
    public static ResponseEntity<ResponseDTO> noExistUser() {
        ResponseDTO result = new ResponseDTO(ResponseCode.NOT_EXISTED_USER, ResponseMessage.NOT_EXISTED_USER);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }


}
