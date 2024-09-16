package junsu.personal.dto.response.crawling;

import junsu.personal.common.ResponseCode;
import junsu.personal.common.ResponseMessage;
import junsu.personal.dto.object.CrawlingDTO;
import junsu.personal.dto.response.ResponseDTO;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Getter
public class GetCrawlingResponseDTO extends ResponseDTO {
    List<CrawlingDTO> crawlingList;

    private GetCrawlingResponseDTO(List<CrawlingDTO> crawlingList) {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.crawlingList = crawlingList;
    }

    public static ResponseEntity<GetCrawlingResponseDTO> success(List<CrawlingDTO> crawlingList) {
        GetCrawlingResponseDTO result = new GetCrawlingResponseDTO(crawlingList);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
