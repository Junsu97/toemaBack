package junsu.personal.dto.object;

import java.util.List;

public record WeatherAPIDTO(
        List<WeatherIconDesc> weather,
        WeatherMain main,
        String id,
        String name
) {

}
