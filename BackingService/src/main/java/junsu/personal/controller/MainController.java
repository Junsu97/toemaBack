package junsu.personal.controller;

import junsu.personal.dto.response.api.JejuApiResponseDTO;
import junsu.personal.service.IApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/main")
@RequiredArgsConstructor
public class MainController {
    private final IApiService apiService;

    @GetMapping("/main/{grade}")
    public ResponseEntity<? super JejuApiResponseDTO> getDate(@PathVariable String grade){
        ResponseEntity<? super JejuApiResponseDTO> response = apiService.getApiDate(grade);
        return response;
    }
}
