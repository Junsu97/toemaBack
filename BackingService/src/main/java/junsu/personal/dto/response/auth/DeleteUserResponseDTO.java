package junsu.personal.dto.response.auth;

import junsu.personal.common.ResponseCode;
import junsu.personal.common.ResponseMessage;
import junsu.personal.dto.response.ResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class DeleteUserResponseDTO extends ResponseDTO {
    private DeleteUserResponseDTO(){
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
    }
    public static ResponseEntity<? super DeleteUserResponseDTO> success(){
        DeleteUserResponseDTO result = new DeleteUserResponseDTO();
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

}