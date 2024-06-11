package junsu.personal.dto.response.api;

import io.swagger.annotations.Api;
import junsu.personal.common.ResponseCode;
import junsu.personal.common.ResponseMessage;
import junsu.personal.dto.object.ApiDTO;
import junsu.personal.dto.response.ResponseDTO;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Getter
public class JejuApiResponseDTO extends ResponseDTO {
    ApiDTO result;

    private JejuApiResponseDTO(ApiDTO result){
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.result = result;
    }

    public static ResponseEntity<JejuApiResponseDTO> success(ApiDTO list){
        JejuApiResponseDTO result = new JejuApiResponseDTO(list);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
