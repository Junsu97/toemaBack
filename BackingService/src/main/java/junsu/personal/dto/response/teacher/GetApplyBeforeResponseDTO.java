package junsu.personal.dto.response.teacher;

import junsu.personal.common.ResponseCode;
import junsu.personal.common.ResponseMessage;
import junsu.personal.dto.response.ResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class GetApplyBeforeResponseDTO extends ResponseDTO {
    private GetApplyBeforeResponseDTO(){
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
    }

    public static ResponseEntity<GetApplyBeforeResponseDTO> success(){
        GetApplyBeforeResponseDTO result = new GetApplyBeforeResponseDTO();
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    public static ResponseEntity<ResponseDTO> duplicateApply(){
        ResponseDTO result = new ResponseDTO(ResponseCode.DUPLICATE_APPLY, ResponseMessage.DUPLICATE_APPLY);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }

}
