package junsu.personal.service.impl;

import junsu.personal.dto.response.ResponseDTO;
import junsu.personal.dto.response.search.GetPopularListResponseDTO;
import junsu.personal.repository.SearchLogRepository;
import junsu.personal.repository.resultSet.GetFavoriteListResultSet;
import junsu.personal.repository.resultSet.GetPopularListResultSet;
import junsu.personal.service.ISearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchService implements ISearchService {
    private final SearchLogRepository searchLogRepository;

    @Override
    public ResponseEntity<? super GetPopularListResponseDTO> getPopularList() {
        List<GetPopularListResultSet> resultSets = new ArrayList<>();
        try{
            resultSets = searchLogRepository.getPopularList();
        }catch (Exception e){
            e.printStackTrace();
            return ResponseDTO.databaseError();
        }
        return GetPopularListResponseDTO.success(resultSets);
    }
}
