package junsu.personal.dto.response.teacher;

import junsu.personal.common.ResponseCode;
import junsu.personal.common.ResponseMessage;
import junsu.personal.dto.object.TeacherListDTO;
import junsu.personal.dto.response.ResponseDTO;
import junsu.personal.entity.TeacherSubjectEntity;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Getter
public class GetTeacherListResponseDTO extends ResponseDTO {
    private List<TeacherListDTO> teacherList;
    private GetTeacherListResponseDTO(List<TeacherSubjectEntity> teacherSubjectEntities){
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.teacherList = TeacherListDTO.getList(teacherSubjectEntities);
    }

    public static ResponseEntity<GetTeacherListResponseDTO> success(List<TeacherSubjectEntity> teacherSubjectEntities){
        GetTeacherListResponseDTO result = new GetTeacherListResponseDTO(teacherSubjectEntities);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
