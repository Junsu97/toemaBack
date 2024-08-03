package junsu.personal.service;

import feign.Param;
import feign.RequestLine;
import junsu.personal.dto.object.WeatherAPIDTO;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "weatherClient", url = "https://api.openweathermap.org/data/2.5")
public interface IFeignWeatherAPIService {
    @RequestLine("GET /weather?lat={lat}&lon={lon}&appid={apiKey}&units={units}")
    WeatherAPIDTO getWeather(@Param("lat") double latitude,
                             @Param("lon") double longitude,
                             @Param("apiKey") String apiKey,
                             @Param("units") String units);
}
