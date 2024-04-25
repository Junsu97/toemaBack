package junsu.personal.service;

import junsu.personal.dto.response.search.GetPopularListResponseDTO;
import org.springframework.http.ResponseEntity;

public interface ISearchService {
    ResponseEntity<? super GetPopularListResponseDTO> getPopularList();
}
