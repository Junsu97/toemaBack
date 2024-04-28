package junsu.personal.controller;

import junsu.personal.dto.response.search.GetPopularListResponseDTO;
import junsu.personal.dto.response.search.GetRelationListResponseDTO;
import junsu.personal.service.impl.SearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/search")
@RequiredArgsConstructor
@Slf4j
public class SearchController {
    private final SearchService searchService;

    @GetMapping("/popular-list")
    public ResponseEntity<? super GetPopularListResponseDTO> getPopularList(){
        ResponseEntity<? super GetPopularListResponseDTO> response = searchService.getPopularList();
        return response;
    }
    @GetMapping("/{searchWord}/relation-list")
    public ResponseEntity<? super GetRelationListResponseDTO> getRelationList(@PathVariable("searchWord") String searchWord){
        log.info("getRelationList Start!!!!");
        log.info("searchWord : " + searchWord);
        ResponseEntity<? super GetRelationListResponseDTO> response = searchService.getRelationList(searchWord);
        return response;
    }

}
