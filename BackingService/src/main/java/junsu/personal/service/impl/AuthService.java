package junsu.personal.service.impl;

import junsu.personal.dto.request.auth.MailDTO;
import junsu.personal.dto.request.auth.StudentSignUpRequestDTO;
import junsu.personal.dto.request.auth.TeacherSignUpRequestDTO;
import junsu.personal.dto.response.ResponseDTO;
import junsu.personal.dto.response.auth.SignUpResponseDTO;
import junsu.personal.entity.StudentUserEntity;
import junsu.personal.entity.TeacherUserEntity;
import junsu.personal.repository.StudentUserRepository;
import junsu.personal.repository.TeacherUserRepository;
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
    private final TeacherUserRepository teacherUserRepository;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();




    @Override
    public ResponseEntity<? super SignUpResponseDTO> studentSignUp(StudentSignUpRequestDTO pDTO) {

        try {
            String userId = pDTO.userId();
            boolean existedStudentUserId = studentUserRepository.existsByUserId(userId);
            boolean existedTeacherUserId = teacherUserRepository.existsByUserId(userId);
            if (existedStudentUserId || existedTeacherUserId) return SignUpResponseDTO.duplicateUserId();

            String nickname = pDTO.nickname();
            boolean existedStudentNickname = studentUserRepository.existsByNickname(nickname);
            boolean existedTeacherNickname = teacherUserRepository.existsByNickname(nickname);
            if (existedStudentNickname || existedTeacherNickname) return SignUpResponseDTO.duplicateNickname();

            String email = pDTO.email();
            boolean existedStudentEmail = studentUserRepository.existsByEmail(email);
            boolean existedTeacherEmail = teacherUserRepository.existsByEmail(email);
            if (existedStudentEmail || existedTeacherEmail) return SignUpResponseDTO.duplicateEmail();

            String telNumber = pDTO.telNumber();
            boolean existedStudentTelNumber = studentUserRepository.existsByTelNumber(telNumber);
            boolean existedTeacherTelNumber = teacherUserRepository.existsByTelNumber(telNumber);
            if (existedStudentTelNumber || existedTeacherTelNumber) return SignUpResponseDTO.duplicateTelNumber();

            String password = pDTO.password();
            String encodedPassword = passwordEncoder.encode(password);

            String addr = pDTO.addr();
            String addrDetail = pDTO.addrDetail();


            StudentUserEntity studentUserEntity = StudentUserEntity.builder()
                    .userId(userId).userName(pDTO.userName())
                    .password(encodedPassword)
                    .telNumber(telNumber)
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

    @Override
    public ResponseEntity<? super SignUpResponseDTO> teacherSignUp(TeacherSignUpRequestDTO pDTO) {
        try {
            String userId = pDTO.userId();
            boolean existedUserId = studentUserRepository.existsByUserId(userId);
            if (existedUserId) return SignUpResponseDTO.duplicateUserId();

            String nickname = pDTO.nickname();
            boolean existedNickname = studentUserRepository.existsByNickname(nickname);
            if (existedNickname) return SignUpResponseDTO.duplicateNickname();

            String email = pDTO.email();
            boolean existedEmail = studentUserRepository.existsByEmail(email);
            if (existedEmail) return SignUpResponseDTO.duplicateEmail();

            String telNumber = pDTO.telNumber();
            boolean existedTelNumber = studentUserRepository.existsByTelNumber(telNumber);
            if (existedTelNumber) return SignUpResponseDTO.duplicateTelNumber();

            String password = pDTO.password();
            String encodedPassword = passwordEncoder.encode(password);

            String addr = pDTO.addr();
            String addrDetail = pDTO.addrDetail();

            String school = pDTO.school();

            TeacherUserEntity teacherUserEntity = TeacherUserEntity.builder()
                    .userId(userId).userName(pDTO.userName())
                    .password(encodedPassword)
                    .telNumber(telNumber)
                    .school(school)
                    .schoolAuth(false)
                    .email(email)
                    .addr(addr)
                    .addrDetail(addrDetail)
                    .nickname(nickname)

                    .build();

            teacherUserRepository.save(teacherUserEntity);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDTO.databaseError();
        }

        return SignUpResponseDTO.success();
    }

    @Override
    public ResponseEntity<? super SignUpResponseDTO> validateUnivEmail(MailDTO pDTO) {
        try{
            String userId = pDTO.userId();
            TeacherUserEntity entity = TeacherUserEntity.builder()
                    .userId(userId)
                    .schoolAuth(true).build();

            teacherUserRepository.save(entity);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseDTO.databaseError();
        }
        return SignUpResponseDTO.success();
    }


}
