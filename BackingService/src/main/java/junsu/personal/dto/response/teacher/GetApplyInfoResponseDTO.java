package junsu.personal.dto.response.teacher;

import junsu.personal.common.ResponseCode;
import junsu.personal.common.ResponseMessage;
import junsu.personal.dto.response.ResponseDTO;
import junsu.personal.entity.MatchEntity;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class GetApplyInfoResponseDTO extends ResponseDTO {
    private String studentId;
    private String teacherId;
    private String status;
    private String content;
    private String writeDatetime;
    private GetApplyInfoResponseDTO(MatchEntity entity){
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.studentId = entity.getStudentId();
        this.teacherId = entity.getTeacherId();
        this.status = entity.getStatus();
        this.content = entity.getContent();
        this.writeDatetime = entity.getWriteDatetime();
    }
    public static ResponseEntity<GetApplyInfoResponseDTO> success(MatchEntity entity){
        GetApplyInfoResponseDTO result = new GetApplyInfoResponseDTO(entity);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
    public static ResponseEntity<ResponseDTO> notExistUser() {
        ResponseDTO result = new ResponseDTO(ResponseCode.NOT_EXISTED_USER, ResponseMessage.NOT_EXISTED_USER);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);
    }

    public static ResponseEntity<ResponseDTO> notExistApply() {
        ResponseDTO result = new ResponseDTO(ResponseCode.NOT_EXISTED_APPLY, ResponseMessage.NOT_EXISTED_APPLY);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);
    }

}
