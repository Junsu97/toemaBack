package junsu.personal.dto.response.api;

import junsu.personal.common.ResponseCode;
import junsu.personal.common.ResponseMessage;
import junsu.personal.dto.object.ApiDTO;
import junsu.personal.dto.object.WeatherAPIDTO;
import junsu.personal.dto.response.ResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class WeatherAPIResponseDTO extends ResponseDTO {
    WeatherAPIDTO result;
    private WeatherAPIResponseDTO(WeatherAPIDTO result){
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.result = result;
    }

    public static ResponseEntity<WeatherAPIResponseDTO> success(WeatherAPIDTO data){
        WeatherAPIResponseDTO result = new WeatherAPIResponseDTO(data);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
