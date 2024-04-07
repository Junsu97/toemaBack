package junsu.personal.service;

import junsu.personal.dto.request.auth.SignUpRequestDTO;
import junsu.personal.dto.response.auth.SignUpResponseDTO;
import org.springframework.http.ResponseEntity;

public interface IAuthService {
    ResponseEntity<? super SignUpResponseDTO> signUp(SignUpRequestDTO pDTO);
}
