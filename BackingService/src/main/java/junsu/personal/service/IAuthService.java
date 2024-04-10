package junsu.personal.service;

import junsu.personal.dto.request.auth.MailDTO;
import junsu.personal.dto.request.auth.SignInRequestDTO;
import junsu.personal.dto.request.auth.StudentSignUpRequestDTO;
import junsu.personal.dto.request.auth.TeacherSignUpRequestDTO;
import junsu.personal.dto.response.auth.SignInResponseDTO;
import junsu.personal.dto.response.auth.SignUpResponseDTO;
import org.springframework.http.ResponseEntity;

public interface IAuthService {
    ResponseEntity<? super SignUpResponseDTO> studentSignUp(StudentSignUpRequestDTO pDTO);
    ResponseEntity<? super SignUpResponseDTO> teacherSignUp(TeacherSignUpRequestDTO pDTO);

    ResponseEntity<? super SignUpResponseDTO> validateUnivEmail(MailDTO pDTO);

    ResponseEntity<? super SignInResponseDTO> signIn(SignInRequestDTO pDTO);

}
