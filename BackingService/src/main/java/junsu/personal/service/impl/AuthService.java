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
import junsu.personal.entity.StudentUserEntity;
import junsu.personal.entity.TeacherSubjectEntity;
import junsu.personal.entity.TeacherUserEntity;
import junsu.personal.entity.domain.LoginHistoryDomain;
import junsu.personal.entity.domain.StudentFaceIdDomain;
import junsu.personal.entity.domain.TeacherFaceIdDomain;
import junsu.personal.persistance.IMongoMapper;
import junsu.personal.provider.JwtProvider;
import junsu.personal.repository.*;
import junsu.personal.repository.mongo.MongoLoginHistoryRepository;
import junsu.personal.repository.mongo.MongoStudentFaceIdRepository;
import junsu.personal.repository.mongo.MongoTeacherFaceIdRepository;
import junsu.personal.repository.mongo.object.LoginHistory;
import junsu.personal.service.IAuthService;
import junsu.personal.util.DateUtil;
import junsu.personal.util.EncryptUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
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
    private final MongoLoginHistoryRepository mongoLoginHistoryRepository;
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
                teacherUserRepository.save(teacherUserEntity);
                teacherSubjectRepository.save(subjectEntity);

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

            LoginHistoryDomain historyDomain = mongoLoginHistoryRepository.findByUserId(userId);
            if (historyDomain == null) {
                historyDomain = new LoginHistoryDomain();
                historyDomain.setUserId(userId);
            }
            String now = DateUtil.getDateTime("yyyy-MM-dd");
            historyDomain.getLoginHistoryList().add(new LoginHistory(now));
            mongoMapper.insertLoginHistory(historyDomain);
            token = jwtProvider.create(userId, UserRole.USER.getValue(), userType);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDTO.databaseError();
        }

        return SignInResponseDTO.success(token);
    }

    @Override
    public ResponseEntity<? super PostFaceIdSignInResponseDTO> faceIdSignIn(PostFaceIdSignInRequestDTO pDTO) {
        String token = null;  // 토큰을 저장할 변수
        String userType = pDTO.userType();  // 요청 DTO에서 사용자 유형을 가져옴
        double threshold = 40;  // 임계값 설정
        LandMark landMarks = pDTO.landMarks();  // 요청 DTO에서 랜드마크 가져옴
        double minDistance = Double.MAX_VALUE;  // 최소 거리 초기화
        String userId = "";  // 사용자 ID 초기화
        try {
            if (userType.equals(UserType.STUDENT.getValue())) {  // 사용자 유형이 학생인 경우
                List<StudentFaceIdDomain> faceIdDomains = mongoStudentFaceIdRepository.findAll();  // 모든 학생 얼굴 ID 도메인 가져오기
                log.info("faceIdDomains 사이즈" + faceIdDomains.size());  // 도메인 개수 로그 출력
                log.info("findAll 끝");  // 로그 출력

                for (StudentFaceIdDomain domain : faceIdDomains) {  // 모든 학생 얼굴 ID 도메인에 대해 반복
                    log.info("faceIdDomains values : " + domain.getAccuracy());  // 도메인 정확도 로그 출력
                    log.info("faceIdDomains values : " + domain.getLandMarks());  // 도메인 랜드마크 로그 출력

                    double distance = calculateDifference(landMarks, domain.getLandMarks());  // 현재 랜드마크와 도메인 랜드마크의 거리 계산
                    log.info("distance : " + distance);  // 계산된 거리 로그 출력

                    if (distance < minDistance) {  // 현재 거리 < 최소 거리인 경우
                        minDistance = distance;  // 최소 거리 업데이트
                        log.info("minDistance : " + minDistance);  // 최소 거리 로그 출력
                        log.info("domain user : " + domain.getUserId());  // 도메인 사용자 ID 로그 출력

                        if (minDistance < threshold) {  // 최소 거리 < 임계값인 경우
                            userId = domain.getUserId();  // 사용자 ID 업데이트
                        }
                    }
                }

            } else {  // 사용자 유형이 선생인 경우
                List<TeacherFaceIdDomain> faceIdDomains = mongoTeacherFaceIdRepository.findAll();  // 모든 선생 얼굴 ID 도메인 가져오기
                for (TeacherFaceIdDomain domain : faceIdDomains) {  // 모든 선생 얼굴 ID 도메인에 대해 반복
                    double distance = calculateDifference(landMarks, domain.getLandMarks());  // 현재 랜드마크와 도메인 랜드마크의 거리 계산
                    if (distance < minDistance) {  // 현재 거리 < 최소 거리인 경우
                        minDistance = distance;  // 최소 거리 업데이트
                        log.info("minDistance : " + minDistance, "id : " + domain.getUserId());  // 최소 거리 및 도메인 사용자 ID 로그 출력

                        if (Math.abs(minDistance) < threshold) {  // 최소 거리 < 임계값인 경우
                            userId = domain.getUserId();  // 사용자 ID 업데이트
                        }
                    }
                }
            }
            if (!userId.isEmpty()) {  // 사용자 ID가 존재하는 경우
                LoginHistoryDomain historyDomain = mongoLoginHistoryRepository.findByUserId(userId);
                if (historyDomain == null) {
                    historyDomain = new LoginHistoryDomain();
                    historyDomain.setUserId(userId);
                }
                String now = DateUtil.getDateTime("yyyy-MM-dd");
                historyDomain.getLoginHistoryList().add(new LoginHistory(now));
                mongoMapper.insertLoginHistory(historyDomain);
                token = jwtProvider.create(userId, UserRole.USER.getValue(), userType);  // JWT 토큰 생성
            } else {  // 사용자 ID가 존재하지 않는 경우
                return PostFaceIdSignInResponseDTO.signInFailed();  // 로그인 실패 응답 반환
            }

        } catch (Exception e) {  // 예외 발생 시
            e.printStackTrace();  // 예외 스택 트레이스 출력
            ResponseDTO.databaseError();  // 데이터베이스 오류 응답 반환
        }
        if (!userId.isEmpty()) {  // 사용자 ID가 존재하는 경우
            return PostFaceIdSignInResponseDTO.success(token);  // 성공 응답 반환
        } else {  // 사용자 ID가 존재하지 않는 경우
            return PostFaceIdSignInResponseDTO.signInFailed();  // 로그인 실패 응답 반환
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


    private static double calculateDifference(LandMark landMark1, LandMark landMark2) {
        List<Position> totalPos1 = landMark1.positions();  // 첫 번째 랜드마크의 포지션 목록 가져오기
        List<Position> totalPos2 = landMark2.positions();  // 두 번째 랜드마크의 포지션 목록 가져오기

        double totalDifference = 0.0;  // 전체 거리 차이를 저장할 변수 초기화

        int numPos = totalPos1.size();  // 첫 번째 랜드마크의 포지션 개수 가져오기

        for (int i = 0; i < numPos; i++) {  // 모든 포지션에 대해 반복
            Position pos1 = totalPos1.get(i);  // 첫 번째 랜드마크의 현재 포지션 가져오기
            Position pos2 = totalPos2.get(i);  // 두 번째 랜드마크의 현재 포지션 가져오기
            // 유클리드 거리를 사용하여 두 포지션 사이의 거리 차이 계산
            double distance = Math.sqrt(Math.pow(pos1.x() - pos2.x(), 2) + Math.pow(pos1.y() - pos2.y(), 2));
            totalDifference += distance;  // 계산된 거리를 전체 거리 차이에 더하기
        }

        return totalDifference / totalPos1.size();  // 평균 거리 차이 반환
    }

}
