package junsu.personal.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import junsu.personal.dto.object.ApiDTO;
import junsu.personal.dto.response.ResponseDTO;
import junsu.personal.dto.response.api.JejuApiResponseDTO;
import junsu.personal.service.IApiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import org.springframework.http.*;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ApiService implements IApiService {
    private final RestTemplate restTemplate;

    @Value("${api.service.key}")
    private String apiKey;
    private ApiDTO getData(String grade) {
        String baseUrl = "open.jejudatahub.net/api/proxy/9abD88tb7b8t9b97D9t8D9bttat79Daa";
        String url = UriComponentsBuilder.newInstance()
                .scheme("https")
                .host(baseUrl)
                .pathSegment(apiKey)
                .queryParam("grade",grade)
                .build().toUriString();

        log.info("url : " + url);
        HttpHeaders headers = new HttpHeaders();

        restTemplate.getInterceptors().add(((request, body, execution) -> {
            ClientHttpResponse response = execution.execute(request, body);
            response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
            return response;
        }));

        HttpEntity entity = new HttpEntity(headers);
        ResponseEntity<ApiDTO> response = restTemplate.exchange(url, HttpMethod.GET, entity, ApiDTO.class);
        ObjectMapper objectMapper = new ObjectMapper();
        ApiDTO dataList = null;
        try{
            dataList = objectMapper.convertValue(response.getBody(), ApiDTO.class);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        return dataList;
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
}
