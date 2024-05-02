package junsu.personal.dto.response.user;

import junsu.personal.common.ResponseCode;
import junsu.personal.common.ResponseMessage;
import junsu.personal.dto.response.ResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class PostMailSendResponseDTO extends ResponseDTO {
    private PostMailSendResponseDTO(){
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
    }

    public static ResponseEntity<? super PostMailSendResponseDTO> success(){
        PostMailSendResponseDTO result = new PostMailSendResponseDTO();
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
