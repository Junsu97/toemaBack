package junsu.personal.dto.response.auth;

import junsu.personal.common.ResponseCode;
import junsu.personal.common.ResponseMessage;
import junsu.personal.dto.response.ResponseDTO;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class SignInResponseDTO extends ResponseDTO {
    private String token;
    private long expirationTime;

    private SignInResponseDTO(String token, long expirationTime){
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.token = token;
        this.expirationTime = expirationTime;

    }

    public static ResponseEntity<SignInResponseDTO> success(String token){
        SignInResponseDTO result = new SignInResponseDTO(token, 3600);
        return ResponseEntity.status(HttpStatus.OK).body(result);

    }

    public static ResponseEntity<ResponseDTO> signInFailed(){
        ResponseDTO result = new ResponseDTO(ResponseCode.SIGN_IN_FAIL, ResponseMessage.SIGN_IN_FAIL);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);
    }
}
