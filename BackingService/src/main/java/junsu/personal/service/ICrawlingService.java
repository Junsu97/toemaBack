package junsu.personal.service;

import junsu.personal.dto.object.CrawlingDTO;
import junsu.personal.dto.response.crawling.GetCrawlingResponseDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ICrawlingService {
    ResponseEntity<? super GetCrawlingResponseDTO> getNewsList();
}
