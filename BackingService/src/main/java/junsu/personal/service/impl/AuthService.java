package junsu.personal.service.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.transaction.Transactional;
import junsu.personal.auth.UserRole;
import junsu.personal.auth.UserType;
import junsu.personal.dto.request.auth.*;
import junsu.personal.dto.response.ResponseDTO;
import junsu.personal.dto.response.auth.SignInResponseDTO;
import junsu.personal.dto.response.auth.SignUpResponseDTO;
import junsu.personal.entity.QStudentUserEntity;
import junsu.personal.entity.QTeacherUserEntity;
import junsu.personal.entity.StudentUserEntity;
import junsu.personal.entity.TeacherUserEntity;
import junsu.personal.provider.JwtProvider;
import junsu.personal.repository.StudentUserRepository;
import junsu.personal.repository.TeacherUserRepository;
import junsu.personal.service.IAuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService implements IAuthService {
    private final StudentUserRepository studentUserRepository;
    private final TeacherUserRepository teacherUserRepository;
    private final JwtProvider jwtProvider;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    @Override
    public ResponseEntity<? super SignUpResponseDTO> signUp(SignUpRequestDTO pDTO) {
        log.info(this.getClass().getName() + "signUp Start!!!");
        try{
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
            String role = "ROLE_USER";
            String userType = pDTO.userType();

            if(userType.equalsIgnoreCase(UserType.STUDENT.getValue())){
                StudentUserEntity studentUserEntity = StudentUserEntity.builder()
                        .userId(userId).userName(pDTO.userName())
                        .password(encodedPassword)
                        .telNumber(telNumber)
                        .email(email)
                        .addr(addr)
                        .addrDetail(addrDetail)
                        .nickname(nickname)
                        .role(role)
                        .build();

                studentUserRepository.save(studentUserEntity);
            }else{
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
                        .role(role)

                        .build();

                teacherUserRepository.save(teacherUserEntity);
            }
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseDTO.databaseError();
        }finally {
            log.info(this.getClass().getName() + ".teacherSignUp End!!!!!");
        }

        return SignUpResponseDTO.success();
    }
    @Override
    public ResponseEntity<? super SignUpResponseDTO> validateUnivEmail(MailDTO pDTO) {
        log.info(this.getClass().getName() + ".validateUnivEmail Start!!!!!");
        try {
            String userId = pDTO.userId();
            TeacherUserEntity entity = TeacherUserEntity.builder()
                    .userId(userId)
                    .schoolAuth(true).build();

            teacherUserRepository.save(entity);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDTO.databaseError();
        }finally {
            log.info(this.getClass().getName() + ".validateUnivEmail End!!!!!");
        }

        return SignUpResponseDTO.success();
    }

    @Override
    public ResponseEntity<? super SignInResponseDTO> signIn(SignInRequestDTO pDTO) {

        log.info(this.getClass().getName() + ".signIn Start!!!!!");
        String token = null;
        try {
            String userId = pDTO.userId();
            String password = pDTO.password();
            String encodedPassword = null;
            String userType = "";
            if (pDTO.userType().equalsIgnoreCase(UserType.STUDENT.getValue())) {
                userType = UserType.STUDENT.getValue();
                StudentUserEntity userEntity = studentUserRepository.findByUserId(userId);

                if (userEntity == null) return SignInResponseDTO.signInFailed();

                encodedPassword = userEntity.getPassword();
                boolean isMatched = passwordEncoder.matches(password, encodedPassword);

                if (!isMatched) return SignInResponseDTO.signInFailed();


            } else if (pDTO.userType().equalsIgnoreCase(UserType.TEACHER.getValue())) {
                userType = UserType.TEACHER.getValue();
                TeacherUserEntity userEntity = teacherUserRepository.findByUserId(userId);

                if (userId == null) return SignInResponseDTO.signInFailed();

                encodedPassword = userEntity.getPassword();
                boolean isMatched = passwordEncoder.matches(password, encodedPassword);

                if (!isMatched) return SignInResponseDTO.signInFailed();
            }


            token = jwtProvider.create(userId, UserRole.USER.getValue(), userType);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDTO.databaseError();
        }finally {
            log.info(this.getClass().getName() + ".signIn End!!!!!");
        }

        return SignInResponseDTO.success(token);
    }


}
