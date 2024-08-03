package junsu.personal.service.impl;

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
//        String baseUrl = "open.jejudatahub.net/api/proxy/9abD88tb7b8t9b97D9t8D9bttat79Daa";
//        String url = UriComponentsBuilder.newInstance()
//                .scheme("https")
//                .host(baseUrl)
//                .pathSegment(apiKey)
//                .queryParam("grade",grade)
//                .build().toUriString();
//
//        log.info("url : " + url);
//        HttpHeaders headers = new HttpHeaders();
//
//        restTemplate.getInterceptors().add(((request, body, execution) -> {
//            ClientHttpResponse response = execution.execute(request, body);
//            response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
//            return response;
//        }));
//
//        HttpEntity entity = new HttpEntity(headers);
//        ResponseEntity<ApiDTO> response = restTemplate.exchange(url, HttpMethod.GET, entity, ApiDTO.class);
//        ObjectMapper objectMapper = new ObjectMapper();
//        ApiDTO dataList = null;
//        try{
//            dataList = objectMapper.convertValue(response.getBody(), ApiDTO.class);
//        }catch (Exception e){
//            e.printStackTrace();
//            return null;
//        }
//        return dataList;
        ApiDTO result = null;
        try{
            result = feignAPIService.getData(apiKey,grade);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            return result;
        }

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

    public ResponseEntity<? super JejuApiResponseDTO> getApiDate(String grade){
        ApiDTO data = null;
        try{
            data = getData(grade);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseDTO.validationFailed();
        }
        return JejuApiResponseDTO.success(data);
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
