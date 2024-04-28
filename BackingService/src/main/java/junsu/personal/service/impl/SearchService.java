package junsu.personal.service.impl;

import junsu.personal.dto.response.ResponseDTO;
import junsu.personal.dto.response.search.GetPopularListResponseDTO;
import junsu.personal.dto.response.search.GetRelationListResponseDTO;
import junsu.personal.repository.SearchLogRepository;
import junsu.personal.repository.resultSet.GetFavoriteListResultSet;
import junsu.personal.repository.resultSet.GetPopularListResultSet;
import junsu.personal.repository.resultSet.GetRelationListResultSet;
import junsu.personal.service.ISearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
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

    @Override
    public ResponseEntity<? super GetRelationListResponseDTO> getRelationList(String searchWord) {
        List<GetRelationListResultSet> resultSet = new ArrayList<>();
        try{
            resultSet = searchLogRepository.getRelationList(searchWord);
            for(GetRelationListResultSet rs : resultSet){
                log.info("getWord : " + rs.getSearchWord());
            }
        }catch (Exception e){
            e.printStackTrace();
            ResponseDTO.databaseError();
        }
        return GetRelationListResponseDTO.success(resultSet);
    }


}
