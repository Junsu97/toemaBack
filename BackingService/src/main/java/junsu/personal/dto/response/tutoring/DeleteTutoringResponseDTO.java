package junsu.personal.dto.response.tutoring;

import junsu.personal.common.ResponseCode;
import junsu.personal.common.ResponseMessage;
import junsu.personal.dto.response.ResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class DeleteTutoringResponseDTO extends ResponseDTO {
    private DeleteTutoringResponseDTO() {super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);}

    public static ResponseEntity<DeleteTutoringResponseDTO> success(){
        DeleteTutoringResponseDTO result = new DeleteTutoringResponseDTO();
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
    public static ResponseEntity<ResponseDTO> noExistTutoring(){
        ResponseDTO result = new ResponseDTO(ResponseCode.NOT_EXISTED_TUTORING, ResponseMessage.NOT_EXISTED_TUTORING);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }
    public static ResponseEntity<ResponseDTO> noExistUser(){
        ResponseDTO result = new ResponseDTO(ResponseCode.NOT_EXISTED_USER, ResponseMessage.NOT_EXISTED_USER);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }

    public static ResponseEntity<ResponseDTO> noPermission() {
        ResponseDTO result = new ResponseDTO(ResponseCode.NO_PERMISSION, ResponseMessage.NO_PERMISSION);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(result);
    }
}
