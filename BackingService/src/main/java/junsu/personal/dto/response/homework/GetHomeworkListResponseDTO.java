package junsu.personal.dto.response.homework;

import junsu.personal.common.ResponseCode;
import junsu.personal.common.ResponseMessage;
import junsu.personal.dto.object.HomeworkListDTO;
import junsu.personal.dto.response.ResponseDTO;
import junsu.personal.entity.HomeworkEntity;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Getter
public class GetHomeworkListResponseDTO extends ResponseDTO {

    private List<HomeworkListDTO> homeworkList;
    private GetHomeworkListResponseDTO(List<HomeworkEntity> entities){
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.homeworkList = HomeworkListDTO.getList(entities);
    }

    public static ResponseEntity<ResponseDTO> noExistUser() {
        ResponseDTO result = new ResponseDTO(ResponseCode.NOT_EXISTED_USER, ResponseMessage.NOT_EXISTED_USER);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }

    public static ResponseEntity<GetHomeworkListResponseDTO> success(List<HomeworkEntity> entities){
        GetHomeworkListResponseDTO result = new GetHomeworkListResponseDTO(entities);
        return ResponseEntity.status(HttpStatus.OK).body(result);

    }

    public static ResponseEntity<ResponseDTO> notExistHomework(){
        ResponseDTO result = new ResponseDTO(ResponseCode.NOT_EXISTED_HOMEWORK, ResponseMessage.NOT_EXISTED_HOMEWORK);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }
    public static ResponseEntity<ResponseDTO> noExistMatch(){
        ResponseDTO result = new ResponseDTO(ResponseCode.NOT_EXISTED_MATCH, ResponseMessage.NOT_EXISTED_MATCH);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

}
