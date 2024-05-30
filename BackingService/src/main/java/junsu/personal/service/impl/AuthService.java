package junsu.personal.service.impl;

import junsu.personal.auth.UserRole;
import junsu.personal.auth.UserType;
import junsu.personal.dto.request.auth.*;
import junsu.personal.dto.request.auth.faceId.PostFaceIDRequestDTO;
import junsu.personal.dto.request.auth.faceId.PostFaceIdSignInRequestDTO;
import junsu.personal.dto.request.auth.faceId.object.LandMark;
import junsu.personal.dto.request.auth.faceId.object.Position;
import junsu.personal.dto.response.ResponseDTO;
import junsu.personal.dto.response.auth.SignInResponseDTO;
import junsu.personal.dto.response.auth.SignUpResponseDTO;
import junsu.personal.dto.response.auth.faceId.PostFaceIdResponseDTO;
import junsu.personal.dto.response.auth.faceId.PostFaceIdSignInResponseDTO;
import junsu.personal.dto.response.user.GetTeacherSubjectResponseDTO;
import junsu.personal.entity.StudentUserEntity;
import junsu.personal.entity.TeacherSubjectEntity;
import junsu.personal.entity.TeacherUserEntity;
import junsu.personal.entity.domain.StudentFaceIdDomain;
import junsu.personal.entity.domain.TeacherFaceIdDomain;
import junsu.personal.persistance.IMongoMapper;
import junsu.personal.provider.JwtProvider;
import junsu.personal.repository.*;
import junsu.personal.repository.mongo.MongoStudentFaceIdRepository;
import junsu.personal.repository.mongo.MongoTeacherFaceIdRepository;
import junsu.personal.service.IAuthService;
import junsu.personal.util.EncryptUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService implements IAuthService {
    private final StudentUserRepository studentUserRepository;
    private final TeacherSubjectRepository teacherSubjectRepository;
    private final TeacherUserRepository teacherUserRepository;
    private final MongoStudentFaceIdRepository mongoStudentFaceIdRepository;
    private final MongoTeacherFaceIdRepository mongoTeacherFaceIdRepository;
    private final IMongoMapper mongoMapper;
    private final JwtProvider jwtProvider;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    @Override
    public ResponseEntity<? super SignUpResponseDTO> signUp(SignUpRequestDTO pDTO) {
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
            String role = "ROLE_USER";
            String userType = pDTO.userType();


            if (userType.equalsIgnoreCase(UserType.STUDENT.getValue())) {
                String school = pDTO.school();
                if (pDTO.school() == null || pDTO.school().isEmpty()) {
                    school = "";
                }

                StudentUserEntity studentUserEntity = StudentUserEntity.builder()
                        .userId(userId).userName(pDTO.userName())
                        .password(encodedPassword)
                        .telNumber(EncryptUtil.encAES128CBC(telNumber))
                        .email(EncryptUtil.encAES128CBC(email))
                        .emailAuth(false)
                        .addr(addr)
                        .school(school)
                        .addrDetail(addrDetail)
                        .nickname(nickname)
                        .role(role)
                        .build();

                studentUserRepository.save(studentUserEntity);
            } else {
                String school = pDTO.school();
                TeacherUserEntity teacherUserEntity = TeacherUserEntity.builder()
                        .userId(userId).userName(pDTO.userName())
                        .password(encodedPassword)
                        .telNumber(EncryptUtil.encAES128CBC(telNumber))
                        .school(school)
                        .emailAuth(false)
                        .email(EncryptUtil.encAES128CBC(email))
                        .addr(addr)
                        .addrDetail(addrDetail)
                        .nickname(nickname)
                        .role(role)
                        .build();

                TeacherSubjectEntity subjectEntity = TeacherSubjectEntity.builder()
                        .userId(userId)
                        .desc("")
                        .build();
                teacherSubjectRepository.save(subjectEntity);
                teacherUserRepository.save(teacherUserEntity);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDTO.databaseError();
        }
        return SignUpResponseDTO.success();
    }

    @Override
    public ResponseEntity<? super SignUpResponseDTO> validateUnivEmail(MailDTO pDTO) {
        try {
            String userId = pDTO.userId();
            TeacherUserEntity entity = TeacherUserEntity.builder()
                    .userId(userId)
                    .emailAuth(true).build();

            teacherUserRepository.save(entity);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDTO.databaseError();
        }

        return SignUpResponseDTO.success();
    }

    @Override
    public ResponseEntity<? super SignInResponseDTO> signIn(SignInRequestDTO pDTO) {
        String token = null;
        try {
            String userId = pDTO.userId();
            String password = pDTO.password();
            String encodedPassword = null;
            String userType = "";
            if (pDTO.userType().equalsIgnoreCase(UserType.STUDENT.getValue())) {
                userType = UserType.STUDENT.getValue();

                boolean isEmpty = studentUserRepository.existsByUserId(pDTO.userId());
                if(!isEmpty){
                    return SignInResponseDTO.signInFailed();
                }
                StudentUserEntity userEntity = studentUserRepository.findByUserId(userId);

                if (userEntity == null) return SignInResponseDTO.signInFailed();

                encodedPassword = userEntity.getPassword();
                boolean isMatched = passwordEncoder.matches(password, encodedPassword);

                if (!isMatched) return SignInResponseDTO.signInFailed();


            } else if (pDTO.userType().equalsIgnoreCase(UserType.TEACHER.getValue())) {
                userType = UserType.TEACHER.getValue();
                boolean isEmpty = teacherUserRepository.existsByUserId(userId);
                log.info("isEmpty : " + isEmpty);
                if(!isEmpty) return SignInResponseDTO.signInFailed();

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
        }

        return SignInResponseDTO.success(token);
    }

    @Override
    public ResponseEntity<? super PostFaceIdSignInResponseDTO> faceIdSignIn(PostFaceIdSignInRequestDTO pDTO) {
        String token = null;
        String userType = pDTO.userType();
        double threshold  = 40;
        LandMark landMarks = pDTO.landMarks();
        double minDistance =Double.MAX_VALUE;
        String userId = "";
        try {
            if (userType.equals(UserType.STUDENT.getValue())) {
                List<StudentFaceIdDomain> faceIdDomains = mongoStudentFaceIdRepository.findAll();
                log.info("faceIdDomains 사이즈" + faceIdDomains.size());
                log.info("findAll 끝");
                for(StudentFaceIdDomain domain : faceIdDomains){
                    log.info("faceIdDomains values : " + domain.getAccuracy());
                    log.info("faceIdDomains values : " + domain.getLandMarks());
                    double distance = calculateDifference(landMarks, domain.getLandMarks());
                    log.info("distance : " + distance);
                    if(distance < minDistance){
                        minDistance = distance;
                        log.info("minDistance : " + minDistance);
                        log.info("domain user : " + domain.getUserId());
                        if(minDistance < threshold){
                            userId = domain.getUserId();
                        }
                    }
                }

            } else {
                List<TeacherFaceIdDomain> faceIdDomains = mongoTeacherFaceIdRepository.findAll();

                for(TeacherFaceIdDomain domain : faceIdDomains){
                    double distance = calculateDifference(landMarks, domain.getLandMarks());
                    if(distance < minDistance){
                        minDistance = distance;

                        log.info("minDistance : " + minDistance, "id : " + domain.getUserId());
                        if(Math.abs(minDistance) < threshold){
                            userId = domain.getUserId();
                        }
                    }

                }
            }
            if(!userId.isEmpty()){
                token = jwtProvider.create(userId, UserRole.USER.getValue(), userType);
            }else{
                return PostFaceIdSignInResponseDTO.signInFailed();

            }
        }catch (Exception e){
            e.printStackTrace();
            ResponseDTO.databaseError();
        }
        if(!userId.isEmpty()){
            return PostFaceIdSignInResponseDTO.success(token);
        }else{
            return PostFaceIdSignInResponseDTO.signInFailed();
        }
    }

    @Override
    public ResponseEntity<? super PostFaceIdResponseDTO> postFaceId(PostFaceIDRequestDTO pDTO) {
        try {
            log.info("FaceID 등록을 위한 UserID : " + pDTO.userId());
            int res = mongoMapper.insertFaceId(pDTO);

            if (res != 1) {
                ResponseDTO.databaseError();
            }

            if (pDTO.userType().equals(UserType.STUDENT.getValue())) {
                StudentUserEntity userEntity = studentUserRepository.findByUserId(pDTO.userId());
                userEntity = userEntity.toBuilder().faceId(true).build();
                studentUserRepository.save(userEntity);
            } else {
                TeacherUserEntity userEntity = teacherUserRepository.findByUserId(pDTO.userId());
                userEntity = userEntity.toBuilder().faceId(true).build();
                teacherUserRepository.save(userEntity);
            }
        } catch (Exception e) {
            e.printStackTrace();
            ResponseDTO.databaseError();
        }

        return PostFaceIdResponseDTO.success();
    }


    private static double calculateDifference(LandMark landMark1, LandMark landMark2){
        List<Position> totalPos1 = landMark1.positions();
        List<Position> totalPos2 = landMark2.positions();

        double totalDifference = 0.0;

        int numPos = totalPos1.size();

        for(int i = 0; i < numPos; i++){
            Position pos1 = totalPos1.get(i);
            Position pos2 = totalPos2.get(i);
            // 유클리드 거리를 사용하여 두 포지션 사이의 거리 차이 계산
            double distance = Math.sqrt(Math.pow(pos1.x() - pos2.x(),2) + Math.pow(pos1.y() - pos2.y(), 2));
            totalDifference += distance;

        }

        return totalDifference / totalPos1.size();
    }
}
