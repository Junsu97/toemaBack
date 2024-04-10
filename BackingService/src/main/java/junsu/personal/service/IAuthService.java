package junsu.personal.service;

import junsu.personal.dto.request.auth.*;
import junsu.personal.dto.response.auth.SignInResponseDTO;
import junsu.personal.dto.response.auth.SignUpResponseDTO;
import org.springframework.http.ResponseEntity;

public interface IAuthService {
    ResponseEntity<? super SignUpResponseDTO> signUp(SignUpRequestDTO pDTO);

    ResponseEntity<? super SignUpResponseDTO> validateUnivEmail(MailDTO pDTO);

    ResponseEntity<? super SignInResponseDTO> signIn(SignInRequestDTO pDTO);

}
