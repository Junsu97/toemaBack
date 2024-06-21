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
public class GetUserResponseDTO extends ResponseDTO {
    private String userId;
    private String nickname;
    private String profileImage;
    private String addr;
    private String addrDetail;
    private String school;
    private Boolean emailAuth;
    private String userType;

    private GetUserResponseDTO(StudentUserEntity userEntity){
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.userId = userEntity.getUserId();
        this.nickname = userEntity.getNickname();
        this.profileImage = userEntity.getProfileImage();
        this.emailAuth = userEntity.getEmailAuth();
        this.school = userEntity.getSchool();
        this.addr = userEntity.getAddr();
        this.addrDetail = userEntity.getAddrDetail();
        this.userType = "STUDENT";
    }
    private GetUserResponseDTO(TeacherUserEntity userEntity){
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.userId = userEntity.getUserId();
        this.nickname = userEntity.getNickname();
        this.profileImage = userEntity.getProfileImage();
        this.school = userEntity.getSchool();
        this.emailAuth = userEntity.getEmailAuth();
        this.addr = userEntity.getAddr();
        this.addrDetail = userEntity.getAddrDetail();
        this.userType = "TEACHER";
    }

    public static ResponseEntity<GetUserResponseDTO> success(StudentUserEntity userEntity){
        GetUserResponseDTO result = new GetUserResponseDTO(userEntity);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
    public static ResponseEntity<GetUserResponseDTO> success(TeacherUserEntity userEntity){
        GetUserResponseDTO result = new GetUserResponseDTO(userEntity);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    public static ResponseEntity<ResponseDTO> notExistUser() {
        ResponseDTO result = new ResponseDTO(ResponseCode.NOT_EXISTED_USER, ResponseMessage.NOT_EXISTED_USER);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);
    }
}

