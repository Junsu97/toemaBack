package junsu.personal.dto.response.user;

import junsu.personal.common.ResponseCode;
import junsu.personal.common.ResponseMessage;
import junsu.personal.dto.response.ResponseDTO;
import junsu.personal.entity.TeacherSubjectEntity;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class GetTeacherSubjectResponseDTO extends ResponseDTO {
    private Boolean korean;
    private Boolean math;
    private Boolean science;
    private Boolean social;
    private Boolean english;
    private String desc;

    private GetTeacherSubjectResponseDTO(TeacherSubjectEntity subjectEntity){
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.korean = subjectEntity.getKorean();
        this.math = subjectEntity.getMath();
        this.social = subjectEntity.getSocial();
        this.science = subjectEntity.getScience();
        this.english = subjectEntity.getEnglish();
        this.desc = subjectEntity.getDesc();
    }

    public static ResponseEntity<GetTeacherSubjectResponseDTO> success(TeacherSubjectEntity subjectEntity){
        GetTeacherSubjectResponseDTO result = new GetTeacherSubjectResponseDTO(subjectEntity);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    public static ResponseEntity<ResponseDTO> noExistUser() {
        ResponseDTO result = new ResponseDTO(ResponseCode.NOT_EXISTED_USER, ResponseMessage.NOT_EXISTED_USER);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);
    }
}
