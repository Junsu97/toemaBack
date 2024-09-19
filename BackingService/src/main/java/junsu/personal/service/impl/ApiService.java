package junsu.personal.service.impl;

import feign.FeignException;
import junsu.personal.dto.object.ApiDTO;
import junsu.personal.dto.object.WeatherAPIDTO;
import junsu.personal.dto.response.ResponseDTO;
import junsu.personal.dto.response.api.JejuApiResponseDTO;
import junsu.personal.dto.response.api.WeatherAPIResponseDTO;
import junsu.personal.service.IApiService;
import junsu.personal.service.IFeignJejuAPIService;
import junsu.personal.service.IFeignWeatherAPIService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import org.springframework.http.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class ApiService implements IApiService {
    private final RestTemplate restTemplate;
    private final IFeignJejuAPIService feignAPIService;
    private final IFeignWeatherAPIService feignWeatherAPIService;

    @Value("${api.service.key}")
    private String apiKey;

    @Value("${api.weather.key}")
    private String weatherKey;

    private ApiDTO getData(String grade) {
        ApiDTO result = null;
        try{result = feignAPIService.getData(apiKey,grade);}
        catch (Exception e){e.printStackTrace();}
        finally {return result;}

    }

    private WeatherAPIDTO getWeatherAPI(double lat, double lon) {
        WeatherAPIDTO result = null;
        try{
            result = feignWeatherAPIService.getWeather(lat, lon, weatherKey, "metric");
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            return result;
        }
    }

    @Retryable(value = { FeignException.class }, maxAttempts = 5, backoff = @Backoff(delay = 2000))
    public ResponseEntity<? super JejuApiResponseDTO> getApiData(String grade) {
        try {
            log.info("리트라이");
            ApiDTO result = getData(grade);
            return JejuApiResponseDTO.success(result);
        } catch (FeignException e) {
            // 예외 처리 로직 추가
            return recover(e, grade);
        }
    }

    @Recover
    public ResponseEntity<? super JejuApiResponseDTO> recover(FeignException e, String grade) {
        // 재시도 실패 시 처리 로직
        e.printStackTrace();
        return JejuApiResponseDTO.validationFailed();
    }


    @Override
    public ResponseEntity<? super WeatherAPIResponseDTO> getWeather(double lat, double lon) {
        WeatherAPIDTO data = null;
        try{
            data = getWeatherAPI(lat, lon);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseDTO.validationFailed();
        }
        return WeatherAPIResponseDTO.success(data);
    }
}
