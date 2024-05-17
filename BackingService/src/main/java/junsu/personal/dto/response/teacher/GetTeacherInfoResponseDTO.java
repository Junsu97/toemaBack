package junsu.personal.dto.response.teacher;

import junsu.personal.common.ResponseCode;
import junsu.personal.common.ResponseMessage;
import junsu.personal.dto.response.ResponseDTO;
import junsu.personal.repository.resultSet.GetTeacherInfoResultSet;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class GetTeacherInfoResponseDTO extends ResponseDTO {
    private String userId;
    private String nickname;
    private String school;
    private String profileImage;
    private Boolean korean;
    private Boolean math;
    private Boolean science;
    private Boolean social;
    private Boolean english;
    private String desc;
    private GetTeacherInfoResponseDTO(GetTeacherInfoResultSet resultSet){
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.userId = resultSet.getUserId();
        this.nickname = resultSet.getNickname();
        this.school = resultSet.getSchool();
        this.profileImage = resultSet.getProfileImage();
        this.korean = resultSet.getKorean();
        this.math = resultSet.getMath();
        this.social = resultSet.getSocial();
        this.science = resultSet.getScience();
        this.english = resultSet.getEnglish();
        this.desc = resultSet.getDescription();
    }

    public static ResponseEntity<GetTeacherInfoResponseDTO> success(GetTeacherInfoResultSet resultSet){
        GetTeacherInfoResponseDTO result = new GetTeacherInfoResponseDTO(resultSet);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    public static ResponseEntity<ResponseDTO> noExistsUser(){
        ResponseDTO result = new ResponseDTO(ResponseCode.NOT_EXISTED_USER, ResponseMessage.NOT_EXISTED_USER);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

}
