package junsu.personal.controller;

import junsu.personal.dto.response.search.GetPopularListResponseDTO;
import junsu.personal.service.impl.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/search")
@RequiredArgsConstructor
public class SearchController {
    private final SearchService searchService;

    @GetMapping("/popular-list")
    public ResponseEntity<? super GetPopularListResponseDTO> getPopularList(){
        ResponseEntity<? super GetPopularListResponseDTO> response = searchService.getPopularList();
        return response;
    }

}
