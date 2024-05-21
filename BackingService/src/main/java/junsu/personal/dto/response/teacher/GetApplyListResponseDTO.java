package junsu.personal.dto.response.teacher;

import junsu.personal.common.ResponseCode;
import junsu.personal.common.ResponseMessage;
import junsu.personal.dto.object.ApplyListDTO;
import junsu.personal.dto.response.ResponseDTO;
import junsu.personal.entity.MatchEntity;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Getter
public class GetApplyListResponseDTO extends ResponseDTO {
    private List<ApplyListDTO> applyList;

    private GetApplyListResponseDTO(List<MatchEntity> entities, String userType){
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.applyList = ApplyListDTO.getList(entities, userType);
    }

    public static ResponseEntity<GetApplyListResponseDTO> success(List<MatchEntity> entities, String userType){
        GetApplyListResponseDTO result = new GetApplyListResponseDTO(entities, userType);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
