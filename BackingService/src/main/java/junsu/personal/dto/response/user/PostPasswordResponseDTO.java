package junsu.personal.dto.response.user;

import junsu.personal.common.ResponseCode;
import junsu.personal.common.ResponseMessage;
import junsu.personal.dto.response.ResponseDTO;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class PostPasswordResponseDTO extends ResponseDTO {
    private String password;
    private PostPasswordResponseDTO(String password) {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.password = password;
    }

    public static ResponseEntity<PostPasswordResponseDTO> success(String password){
        PostPasswordResponseDTO result = new PostPasswordResponseDTO(password);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    public static ResponseEntity<ResponseDTO> notExistUser() {
        ResponseDTO result = new ResponseDTO(ResponseCode.NOT_EXISTED_USER, ResponseMessage.NOT_EXISTED_USER);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);
    }
}
