package junsu.personal.dto.response.user;

import junsu.personal.common.ResponseCode;
import junsu.personal.common.ResponseMessage;
import junsu.personal.dto.response.ResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class PostPasswordCheckResponseDTO extends ResponseDTO {
    private PostPasswordCheckResponseDTO() {super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);}

    public static ResponseEntity<PostPasswordCheckResponseDTO> success(){
        PostPasswordCheckResponseDTO result = new PostPasswordCheckResponseDTO();
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    public static ResponseEntity<ResponseDTO> notExistUser() {
        ResponseDTO result = new ResponseDTO(ResponseCode.NOT_EXISTED_USER, ResponseMessage.NOT_EXISTED_USER);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);
    }
    public static ResponseEntity<ResponseDTO> validateUniversityEmailVerification(){
        ResponseDTO result = new ResponseDTO(ResponseCode.VALIDATION_FAILED, ResponseMessage.VALIDATION_FAILED);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }
}
