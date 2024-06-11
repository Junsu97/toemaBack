package junsu.personal.service;

import junsu.personal.dto.object.ApiDTO;
import junsu.personal.dto.response.api.JejuApiResponseDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IApiService {

    ResponseEntity<? super JejuApiResponseDTO> getApiDate(String grade);
}
