package junsu.personal.dto.response.search;

import junsu.personal.common.ResponseCode;
import junsu.personal.common.ResponseMessage;
import junsu.personal.dto.response.ResponseDTO;
import junsu.personal.repository.resultSet.GetPopularListResultSet;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

@Getter
public class GetPopularListResponseDTO extends ResponseDTO {
    private List<String> popularWordList;

    private GetPopularListResponseDTO(List<GetPopularListResultSet> resultSets){
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        List<String> popularWordList = new ArrayList<>();
         for(GetPopularListResultSet resultSet : resultSets){
             String popularWord = resultSet.getSearchWord();
             popularWordList.add(popularWord);
         }
        this.popularWordList = popularWordList;
    }

    public static ResponseEntity<GetPopularListResponseDTO> success(List<GetPopularListResultSet> resultSets){
        GetPopularListResponseDTO result = new GetPopularListResponseDTO(resultSets);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
