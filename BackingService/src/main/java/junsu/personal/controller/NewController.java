package junsu.personal.controller;

import junsu.personal.dto.response.crawling.GetCrawlingResponseDTO;
import junsu.personal.service.ICrawlingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/news")
@RequiredArgsConstructor
public class NewController {
    private final ICrawlingService crawlingService;

    @GetMapping("/list")
    public ResponseEntity<? super GetCrawlingResponseDTO> getNewsList(){
        ResponseEntity<? super GetCrawlingResponseDTO> response = crawlingService.getNewsList();
        return response;
    }
}
