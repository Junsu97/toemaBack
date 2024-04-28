package junsu.personal.dto.response.search;

import junsu.personal.common.ResponseCode;
import junsu.personal.common.ResponseMessage;
import junsu.personal.dto.response.ResponseDTO;
import junsu.personal.repository.resultSet.GetRelationListResultSet;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

@Getter
public class GetRelationListResponseDTO extends ResponseDTO {
    private List<String> relativeWordList;

    private GetRelationListResponseDTO(List<GetRelationListResultSet> resultSets){
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        List<String> relativeWordList = new ArrayList<>();
        for(GetRelationListResultSet resultSet : resultSets){
            String relativeWord = resultSet.getSearchWord();
            relativeWordList.add(relativeWord);
        }
        this.relativeWordList = relativeWordList;
    }

    public static ResponseEntity<GetRelationListResponseDTO> success(List<GetRelationListResultSet> resultSets){
        GetRelationListResponseDTO result = new GetRelationListResponseDTO(resultSets);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
