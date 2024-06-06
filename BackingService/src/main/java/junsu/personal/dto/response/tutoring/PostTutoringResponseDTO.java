package junsu.personal.dto.response.tutoring;

import junsu.personal.common.ResponseCode;
import junsu.personal.common.ResponseMessage;
import junsu.personal.dto.response.ResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class PostTutoringResponseDTO extends ResponseDTO {
    private PostTutoringResponseDTO() {super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);}

    public static ResponseEntity<PostTutoringResponseDTO> success(){
        PostTutoringResponseDTO result = new PostTutoringResponseDTO();
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    public static ResponseEntity<ResponseDTO> noExistUser(){
        ResponseDTO result = new ResponseDTO(ResponseCode.NOT_EXISTED_USER, ResponseMessage.NOT_EXISTED_USER);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
    public static ResponseEntity<ResponseDTO> noExistMatch(){
        ResponseDTO result = new ResponseDTO(ResponseCode.NOT_EXISTED_MATCH, ResponseMessage.NOT_EXISTED_MATCH);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
    public static ResponseEntity<ResponseDTO> existTutoring(){
        ResponseDTO result = new ResponseDTO(ResponseCode.EXIST_TUTORING, ResponseMessage.EXIST_TUTORING);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
