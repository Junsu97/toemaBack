package junsu.personal.dto.response.homework;

import junsu.personal.common.ResponseCode;
import junsu.personal.common.ResponseMessage;
import junsu.personal.dto.response.ResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class PatchHomeworkResponseDTO extends ResponseDTO {
    private PatchHomeworkResponseDTO(){
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
    }

    public static ResponseEntity<PatchHomeworkResponseDTO> success(){
        PatchHomeworkResponseDTO result = new PatchHomeworkResponseDTO();
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    public static ResponseEntity<ResponseDTO> noExistHomework(){
        ResponseDTO result = new ResponseDTO(ResponseCode.NOT_EXISTED_HOMEWORK, ResponseMessage.NOT_EXISTED_HOMEWORK);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }
}
