package junsu.personal.service;

import junsu.personal.dto.response.api.JejuApiResponseDTO;
import junsu.personal.dto.response.api.WeatherAPIResponseDTO;
import org.springframework.http.ResponseEntity;

public interface IApiService {

    ResponseEntity<? super JejuApiResponseDTO> getApiData(String grade);
    ResponseEntity<? super WeatherAPIResponseDTO> getWeather(double lat, double lon);
}
