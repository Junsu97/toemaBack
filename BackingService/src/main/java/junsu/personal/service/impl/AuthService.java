package junsu.personal.service.impl;

import junsu.personal.dto.request.auth.SignUpRequestDTO;
import junsu.personal.dto.response.ResponseDTO;
import junsu.personal.dto.response.auth.SignUpResponseDTO;
import junsu.personal.entity.StudentUserEntity;
import junsu.personal.repository.StudentUserRepository;
import junsu.personal.service.IAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService implements IAuthService {
    private final StudentUserRepository studentUserRepository;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public ResponseEntity<? super SignUpResponseDTO> signUp(SignUpRequestDTO pDTO) {

        try {
            String userId = pDTO.userId();
            boolean existedUserId = studentUserRepository.existsByUserId(userId);
            if(existedUserId) return SignUpResponseDTO.duplicateUserId();

            String nickname = pDTO.nickname();
            boolean existedNickname = studentUserRepository.existsByNickname(nickname);
            if(existedNickname) return SignUpResponseDTO.duplicateNickname();

            String email = pDTO.email();
            boolean existedEmail = studentUserRepository.existsByEmail(email);
            if(existedEmail) return SignUpResponseDTO.duplicateEmail();

            String telNumber = pDTO.telNumber();
            boolean existedTelNumber = studentUserRepository.existsByTelNumber(telNumber);
            if(existedTelNumber) return SignUpResponseDTO.duplicateTelNumber();

            String password = pDTO.password();
            String encodedPassword = passwordEncoder.encode(password);

            String addr = pDTO.addr();
            String addrDetail = pDTO.addrDetail();

            StudentUserEntity studentUserEntity = StudentUserEntity.builder()
                    .userId(userId).userName(pDTO.userName())
                    .password(encodedPassword)
                    .email(email)
                    .addr(addr)
                    .addrDetail(addrDetail)
                    .nickname(nickname)
                    .build();

            studentUserRepository.save(studentUserEntity);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDTO.databaseError();
        }

        return SignUpResponseDTO.success();
    }
}
