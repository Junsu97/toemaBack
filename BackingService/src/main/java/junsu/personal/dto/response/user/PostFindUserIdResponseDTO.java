package junsu.personal.dto.response.user;

import junsu.personal.common.ResponseCode;
import junsu.personal.common.ResponseMessage;
import junsu.personal.dto.request.user.PostFindUserIdRequestDTO;
import junsu.personal.dto.response.ResponseDTO;
import junsu.personal.entity.StudentUserEntity;
import junsu.personal.entity.TeacherUserEntity;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Slf4j
@Getter
public class PostFindUserIdResponseDTO extends ResponseDTO {
    private String userId;

    private PostFindUserIdResponseDTO(StudentUserEntity userEntity){
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.userId = userEntity.getUserId();
        log.info(userId);
    }
    private PostFindUserIdResponseDTO(TeacherUserEntity userEntity){
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.userId = userEntity.getUserId();
    }
    public static ResponseEntity<PostFindUserIdResponseDTO> success(StudentUserEntity userEntity){
        PostFindUserIdResponseDTO result = new PostFindUserIdResponseDTO(userEntity);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
    public static ResponseEntity<PostFindUserIdResponseDTO> success(TeacherUserEntity userEntity){
        PostFindUserIdResponseDTO result = new PostFindUserIdResponseDTO(userEntity);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    public static ResponseEntity<ResponseDTO> notExistUser() {
        ResponseDTO result = new ResponseDTO(ResponseCode.NOT_EXISTED_USER, ResponseMessage.NOT_EXISTED_USER);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);
    }

}
