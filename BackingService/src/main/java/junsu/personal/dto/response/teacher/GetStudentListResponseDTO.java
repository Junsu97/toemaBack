package junsu.personal.dto.response.teacher;

import junsu.personal.common.ResponseCode;
import junsu.personal.common.ResponseMessage;
import junsu.personal.dto.object.StudentListDTO;
import junsu.personal.dto.response.ResponseDTO;
import junsu.personal.entity.StudentUserEntity;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Getter
public class GetStudentListResponseDTO extends ResponseDTO {
    private List<StudentListDTO> studentList;
    private GetStudentListResponseDTO(List<StudentUserEntity> userEntities){
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.studentList = StudentListDTO.getList(userEntities);
    }

    public static ResponseEntity<GetStudentListResponseDTO> success(List<StudentUserEntity> userEntities){
        GetStudentListResponseDTO result = new GetStudentListResponseDTO(userEntities);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    public static ResponseEntity<ResponseDTO> notExistUser() {
        ResponseDTO result = new ResponseDTO(ResponseCode.NOT_EXISTED_USER, ResponseMessage.NOT_EXISTED_USER);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);
    }
}
