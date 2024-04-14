package junsu.personal.dto.response.user;

import junsu.personal.common.ResponseCode;
import junsu.personal.common.ResponseMessage;
import junsu.personal.dto.response.ResponseDTO;
import junsu.personal.entity.StudentUserEntity;
import junsu.personal.entity.TeacherUserEntity;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class GetSignInUserResponseDTO extends ResponseDTO {
    private String userId;
    private String email;
    private String nickname;
    private String profileImage;
    private String school;
    private Boolean schoolAuth;
    private String faceId;
    private String userType;

    private GetSignInUserResponseDTO(StudentUserEntity userEntity){
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.userId = userEntity.getUserId();
        this.email = userEntity.getEmail();
        this.nickname = userEntity.getNickname();
        this.profileImage = userEntity.getProfileImage();
        this.school = userEntity.getProfileImage();
        this.schoolAuth = null;
        this.faceId = userEntity.getFaceId();
        this.userType = "STUDENT";
    }
    private GetSignInUserResponseDTO(TeacherUserEntity userEntity){
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.userId = userEntity.getUserId();
        this.email = userEntity.getEmail();
        this.nickname = userEntity.getNickname();
        this.profileImage = userEntity.getProfileImage();
        this.school = userEntity.getProfileImage();
        this.schoolAuth = userEntity.getSchoolAuth();
        this.faceId = userEntity.getFaceId();
        this.userType = "TEACHER";
    }
    public static ResponseEntity<GetSignInUserResponseDTO> success(StudentUserEntity userEntity){
        GetSignInUserResponseDTO result = new GetSignInUserResponseDTO(userEntity);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    public static ResponseEntity<GetSignInUserResponseDTO> success(TeacherUserEntity userEntity){
        GetSignInUserResponseDTO result = new GetSignInUserResponseDTO(userEntity);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    public static ResponseEntity<ResponseDTO> notExistUser() {
        ResponseDTO result = new ResponseDTO(ResponseCode.NOT_EXISTED_USER, ResponseMessage.NOT_EXISTED_USER);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);
    }
}