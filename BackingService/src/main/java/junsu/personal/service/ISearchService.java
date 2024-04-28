package junsu.personal.service;

import junsu.personal.dto.response.search.GetPopularListResponseDTO;
import junsu.personal.dto.response.search.GetRelationListResponseDTO;
import org.springframework.http.ResponseEntity;

public interface ISearchService {
    ResponseEntity<? super GetPopularListResponseDTO> getPopularList();
    ResponseEntity<? super GetRelationListResponseDTO> getRelationList(String searchWord);
}
