package junsu.personal.service.impl;

import junsu.personal.dto.request.auth.SignUpRequestDTO;
import junsu.personal.dto.response.ResponseDTO;
import junsu.personal.dto.response.auth.SignUpResponseDTO;
import junsu.personal.repository.StudentUserRepository;
import junsu.personal.service.IAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService implements IAuthService {
    private final StudentUserRepository studentUserRepository;

    @Override
    public ResponseEntity<? super SignUpResponseDTO> signUp(SignUpRequestDTO pDTO) {

        try {
            String userId = pDTO.userId();
            String nickname = pDTO.nickname();
            String telNumber = pDTO.telNumber();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDTO.databaseError();
        }

        return SignUpResponseDTO.success();
    }
}
