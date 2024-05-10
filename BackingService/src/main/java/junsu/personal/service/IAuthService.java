package junsu.personal.service;

import junsu.personal.dto.request.auth.*;
import junsu.personal.dto.request.auth.faceId.PostFaceIDRequestDTO;
import junsu.personal.dto.request.auth.faceId.PostFaceIdSignInRequestDTO;
import junsu.personal.dto.response.auth.SignInResponseDTO;
import junsu.personal.dto.response.auth.SignUpResponseDTO;
import junsu.personal.dto.response.auth.faceId.PostFaceIdResponseDTO;
import junsu.personal.dto.response.auth.faceId.PostFaceIdSignInResponseDTO;
import junsu.personal.dto.response.user.GetTeacherSubjectResponseDTO;
import org.springframework.http.ResponseEntity;

public interface IAuthService {
    ResponseEntity<? super SignUpResponseDTO> signUp(SignUpRequestDTO pDTO);

    ResponseEntity<? super SignUpResponseDTO> validateUnivEmail(MailDTO pDTO);

    ResponseEntity<? super SignInResponseDTO> signIn(SignInRequestDTO pDTO);
    ResponseEntity<? super PostFaceIdSignInResponseDTO> faceIdSignIn(PostFaceIdSignInRequestDTO pDTO);

    ResponseEntity<? super PostFaceIdResponseDTO> postFaceId(PostFaceIDRequestDTO pDTO);


}
