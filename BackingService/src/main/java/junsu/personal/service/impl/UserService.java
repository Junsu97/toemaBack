package junsu.personal.service.impl;

import junsu.personal.auth.UserType;
import junsu.personal.dto.object.MailDTO;
import junsu.personal.dto.request.auth.DeleteUserRequestDTO;
import junsu.personal.dto.request.user.PostMailReceiveRequestDTO;
import junsu.personal.dto.request.user.PostMailSendRequestDTO;
import junsu.personal.dto.request.user.*;
import junsu.personal.dto.response.ResponseDTO;
import junsu.personal.dto.response.auth.DeleteUserResponseDTO;
import junsu.personal.dto.response.user.PostMailReceiveResponseDTO;
import junsu.personal.dto.response.user.PostMailSendResponseDTO;
import junsu.personal.dto.response.auth.SignUpResponseDTO;
import junsu.personal.dto.response.user.*;
import junsu.personal.entity.*;
import junsu.personal.persistance.IMyRedisMapper;
import junsu.personal.repository.*;
import junsu.personal.service.IBoardService;
import junsu.personal.service.IFileService;
import junsu.personal.service.IUserService;
import junsu.personal.util.EncryptUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService implements IUserService {
    private final StudentUserRepository studentUserRepository;
    private final TeacherUserRepository teacherUserRepository;
    private final TeacherSubjectRepository teacherSubjectRepository;
    private final BoardRepository boardRepository;
    private final IBoardService boardService;
    private final ImageRepository imageRepository;
    private final FavoriteRepository favoriteRepository;
    private final CommentRepository commentRepository;
    private final SearchLogRepository searchLogRepository;
    private final BoardListViewRepository boardListViewRepository;
    private final IFileService fileService;
    private final JavaMailSender javaMailSender;
    private final IMyRedisMapper myRedisMapper;
    @Value("${spring.mail.username}")
    private String from;
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public ResponseEntity<? super GetUserResponseDTO> getUser(String userId) {
        StudentUserEntity studentUserEntity = null;
        TeacherUserEntity teacherUserEntity = null;
        try {
            studentUserEntity = studentUserRepository.findByUserId(userId);
            teacherUserEntity = teacherUserRepository.findByUserId(userId);

            if (studentUserEntity == null && teacherUserEntity == null) return GetUserResponseDTO.notExistUser();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDTO.databaseError();
        }

        if (studentUserEntity != null && teacherUserEntity == null) {
            return GetUserResponseDTO.success(studentUserEntity);
        } else {
            return GetUserResponseDTO.success(teacherUserEntity);
        }
    }
    @Override
    public ResponseEntity<? super GetTeacherSubjectResponseDTO> getTeacherSubject(String userId) {
        TeacherSubjectEntity subjectEntity = new TeacherSubjectEntity();
        try{
            subjectEntity = teacherSubjectRepository.findByUserId(userId);
            if(subjectEntity == null) return GetTeacherSubjectResponseDTO.noExistUser();

        }catch (Exception e){
            e.printStackTrace();
            return ResponseDTO.databaseError();
        }
        return GetTeacherSubjectResponseDTO.success(subjectEntity);
    }

    @Override
    public ResponseEntity<? super PostFindUserIdResponseDTO> postFindUserId(PostFindUserIdRequestDTO pDTO) {
        StudentUserEntity studentUserEntity = null;
        TeacherUserEntity teacherUserEntity = null;
        try {
            String email = EncryptUtil.encAES128CBC(pDTO.email());
            if (pDTO.userType().equals(UserType.STUDENT.getValue())) {
                if (studentUserRepository.findByEmailAndUserName(email, pDTO.userName()) == null)
                    return PostFindUserIdResponseDTO.notExistUser();
                else {
                    studentUserEntity = studentUserRepository.findByEmailAndUserName(email, pDTO.userName());
                    return PostFindUserIdResponseDTO.success(studentUserEntity);
                }
            } else {
                if (teacherUserRepository.findByEmailAndUserName(email, pDTO.userName()) == null)
                    return PostFindUserIdResponseDTO.notExistUser();
                else {
                    teacherUserEntity = teacherUserRepository.findByEmailAndUserName(email, pDTO.userName());
                    return PostFindUserIdResponseDTO.success(teacherUserEntity);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDTO.databaseError();
        }
    }

    @Override
    public ResponseEntity<? super PostPasswordResponseDTO> postPassword(PostPasswordRequestDTO pDTO) {
        StudentUserEntity studentUserEntity = null;
        TeacherUserEntity teacherUserEntity = null;
        MailDTO dto = null;
        String tempPassword = getTempPassword();
        String encodedTempPasswrod = passwordEncoder.encode(tempPassword);
        try {
            String email = EncryptUtil.encAES128CBC(pDTO.email());
            dto = MailDTO.builder()
                    .address(pDTO.email())
                    .title("과외해듀오 임시비밀번호 이메일")
                    .message("과외해 듀오 임시 비밀번호 : " + tempPassword)
                    .build();
            if (pDTO.userType().equals(UserType.STUDENT.getValue())) {
                studentUserEntity = studentUserRepository.findByUserIdAndEmailAndUserName(pDTO.userId(), email, pDTO.userName());
                if (studentUserEntity == null) return PostPasswordResponseDTO.notExistUser();
                studentUserEntity = studentUserEntity.toBuilder().password(encodedTempPasswrod).build();
                studentUserRepository.save(studentUserEntity);
            } else {
                teacherUserEntity = teacherUserRepository.findByUserIdAndEmailAndUserName(pDTO.userId(), email, pDTO.userName());
                if (teacherUserEntity == null) return PostPasswordResponseDTO.notExistUser();
                teacherUserEntity = teacherUserEntity.toBuilder().password(encodedTempPasswrod).build();
                teacherUserRepository.save(teacherUserEntity);
            }
            sendMail(dto);
        } catch (Exception e) {
            e.printStackTrace();
            ResponseDTO.databaseError();
        }
        return PostPasswordResponseDTO.success(tempPassword);
    }

    @Override
    public ResponseEntity<? super PostMailSendResponseDTO> postMailSend(PostMailSendRequestDTO pDTO) {
        String title = "과외해듀오 메일 인증 번호";
        String address = pDTO.email();
        String code = this.generateAuthNumber();
        String message = "과외해듀오 메일 인증번호 : " + code;
        log.info("address : " + address);
        try {
            MailDTO mail = new MailDTO(address, title, message);
            sendMail(mail);

            String redisKey = code + address;
            myRedisMapper.saveAuth(redisKey, code);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDTO.databaseError();
        }
        return PostMailSendResponseDTO.success();
    }

    @Override
    public ResponseEntity<? super PostMailReceiveResponseDTO> postMailReceive(PostMailReceiveRequestDTO pDTO) {
        String redisKey = pDTO.code() + pDTO.email();
        try {
            String redisAuthCode = myRedisMapper.getAuth(redisKey);
            if (redisAuthCode == null || !redisAuthCode.equals(pDTO.code())) {
                return PostMailReceiveResponseDTO.authorizationFail();
            }

            String encodedEmail = EncryptUtil.encAES128CBC(pDTO.email());
            if (pDTO.userType().equals(UserType.STUDENT.getValue())) {
                StudentUserEntity userEntity = studentUserRepository.findByEmail(encodedEmail);
                if (userEntity == null) {
                    return PostMailReceiveResponseDTO.notExistUser();
                }
                userEntity = userEntity.toBuilder().emailAuth(true).build();
                studentUserRepository.save(userEntity);

            } else {
                TeacherUserEntity userEntity = teacherUserRepository.findByEmail(encodedEmail);
                if (userEntity == null) {
                    return PostMailReceiveResponseDTO.notExistUser();
                }

                userEntity = userEntity.toBuilder().emailAuth(true).build();
                teacherUserRepository.save(userEntity);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDTO.databaseError();
        }
        return PostMailReceiveResponseDTO.success();
    }

    @Override
    public ResponseEntity<? super PostCheckPasswrodResponseDTO> postCheckPassword(PostCheckPasswordRequestDTO pDTO, String userId) {
        StudentUserEntity studentUserEntity = null;
        TeacherUserEntity teacherUserEntity = null;
        String password = pDTO.password();
        String userType = pDTO.userType();
        try {
            if (userType.equals(UserType.STUDENT.getValue())) {
                studentUserEntity = studentUserRepository.findByUserId(userId);
                if (studentUserEntity == null) return PostCheckPasswrodResponseDTO.notExistUser();
                boolean isMatched = passwordEncoder.matches(password, studentUserEntity.getPassword());
                if (!isMatched) return PostCheckPasswrodResponseDTO.authorizationFail();
            } else {
                teacherUserEntity = teacherUserRepository.findByUserId(userId);
                if (teacherUserEntity == null) return PostCheckPasswrodResponseDTO.notExistUser();
                boolean isMatched = passwordEncoder.matches(password, teacherUserEntity.getPassword());
                if (!isMatched) return PostCheckPasswrodResponseDTO.authorizationFail();
            }
        } catch (Exception e) {
            e.printStackTrace();
            PostCheckPasswrodResponseDTO.databaseError();
        }
        return PostCheckPasswrodResponseDTO.success();
    }

    @Override
    public ResponseEntity<? super GetSignInUserResponseDTO> getSignInUser(String userId) throws Exception {
        StudentUserEntity studentUserEntity = null;
        TeacherUserEntity teacherUserEntity = null;
        try {
            studentUserEntity = studentUserRepository.findByUserId(userId);
            teacherUserEntity = teacherUserRepository.findByUserId(userId);

            if (studentUserEntity == null && teacherUserEntity == null) return GetSignInUserResponseDTO.notExistUser();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDTO.databaseError();
        }

        if (studentUserEntity != null && teacherUserEntity == null) {
            String email = studentUserEntity.getEmail();
            String decodeEmail = EncryptUtil.decAES128CBC(email);
            String decodeTelNum = EncryptUtil.decAES128CBC(studentUserEntity.getTelNumber());
            studentUserEntity = studentUserEntity.toBuilder().email(decodeEmail).telNumber(decodeTelNum).build();
            return GetSignInUserResponseDTO.success(studentUserEntity);
        } else {
            log.info(teacherUserEntity.getAddr());
            String email = teacherUserEntity.getEmail();
            String decodeEmail = EncryptUtil.decAES128CBC(email);
            String decodeTelNum = EncryptUtil.decAES128CBC(teacherUserEntity.getTelNumber());
            teacherUserEntity = teacherUserEntity.toBuilder().email(decodeEmail).telNumber(decodeTelNum).build();
            return GetSignInUserResponseDTO.success(teacherUserEntity);
        }

    }


    @Override
    public ResponseEntity<? super PatchUserResponseDTO> patchUser(PatchUserRequestDTO pDTO, String userId) {
        try{
            String school = pDTO.school();
            if(school == null || school.isEmpty()){
                school = " ";
            }

            if(pDTO.userType().equals(UserType.STUDENT.getValue())){
                StudentUserEntity userEntity = studentUserRepository.findByUserId(userId);
                userEntity = userEntity.toBuilder()
                        .addr(pDTO.addr())
                        .addrDetail(pDTO.addrDetail())
                        .school(pDTO.school())
                        .build();
                studentUserRepository.save(userEntity);
            }else{
                TeacherUserEntity userEntity = teacherUserRepository.findByUserId(userId);
                userEntity = userEntity.toBuilder()
                        .addr(pDTO.addr())
                        .addrDetail(pDTO.addrDetail())
                        .school(pDTO.school())
                        .build();
                TeacherSubjectEntity subjectEntity = teacherSubjectRepository.findByUserId(userId);
                subjectEntity = subjectEntity.toBuilder()
                        .korean(pDTO.korean())
                        .math(pDTO.math())
                        .social(pDTO.social())
                        .science(pDTO.science())
                        .english(pDTO.english())
                        .desc(pDTO.desc())
                        .build();
                teacherUserRepository.save(userEntity);
                teacherSubjectRepository.save(subjectEntity);
            }
        }catch (Exception e){
            e.printStackTrace();
            ResponseDTO.databaseError();
        }
        return PatchUserResponseDTO.success();
    }

    @Override
    public ResponseEntity<? super PatchNicknameResponseDTO> patchNickname(PatchNicknameRequestDTO pDTO, String userId) {
        StudentUserEntity studentUserEntity = null;
        TeacherUserEntity teacherUserEntity = null;
        try {
            studentUserEntity = studentUserRepository.findByUserId(userId);
            teacherUserEntity = teacherUserRepository.findByUserId(userId);
            if (studentUserEntity == null && teacherUserEntity == null) {
                log.info("null임!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                return PatchNicknameResponseDTO.notExistUser();
            }
            String nickname = pDTO.nickname();
            boolean existedStudentNickname = studentUserRepository.existsByNickname(nickname);
            boolean existedTeacherNickname = teacherUserRepository.existsByNickname(nickname);
            if (existedStudentNickname || existedTeacherNickname) return SignUpResponseDTO.duplicateNickname();
            String userType = pDTO.userType();
            if (userType.equalsIgnoreCase(UserType.STUDENT.getValue())) {
                studentUserEntity = studentUserEntity.toBuilder().nickname(nickname).build();
                studentUserRepository.save(studentUserEntity);
            } else {
                teacherUserEntity = teacherUserEntity.toBuilder().nickname(nickname).build();
                teacherUserRepository.save(teacherUserEntity);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDTO.databaseError();
        }

        return PatchNicknameResponseDTO.success();
    }

    @Override
    public ResponseEntity<? super PatchPasswordResponseDTO> patchPassword(PatchPasswordRequestDTO pDTO, String userId) {
        StudentUserEntity studentUserEntity = null;
        TeacherUserEntity teacherUserEntity = null;
        try {
            studentUserEntity = studentUserRepository.findByUserId(userId);
            teacherUserEntity = teacherUserRepository.findByUserId(userId);
            if (studentUserEntity == null && teacherUserEntity == null) return PatchPasswordResponseDTO.notExistUser();
            String userType = pDTO.userType();

            if (userType.equalsIgnoreCase(UserType.STUDENT.getValue())) {

                studentUserEntity = studentUserEntity.toBuilder().password(passwordEncoder.encode(pDTO.newPassword())).build();
                studentUserRepository.save(studentUserEntity);
            } else {

                teacherUserEntity = teacherUserEntity.toBuilder().password(passwordEncoder.encode(pDTO.newPassword())).build();
                teacherUserRepository.save(teacherUserEntity);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDTO.databaseError();
        }

        return PatchPasswordResponseDTO.success();
    }

    @Override
    public ResponseEntity<? super PatchProfileImageResponseDTO> patchProfileImage(PatchProfileImageRequestDTO pDTO, String userId) {
        StudentUserEntity studentUserEntity = null;
        TeacherUserEntity teacherUserEntity = null;
        try {
            studentUserEntity = studentUserRepository.findByUserId(userId);
            teacherUserEntity = teacherUserRepository.findByUserId(userId);
            if (studentUserEntity == null && teacherUserEntity == null)
                return PatchProfileImageResponseDTO.notExistUser();
            String userType = pDTO.userType();

            if (userType.equalsIgnoreCase(UserType.STUDENT.getValue())) {
//                String profileImage = studentUserEntity.getProfileImage();
//                if(!profileImage.isEmpty() || profileImage != null){ fileService.profileImageDelete(profileImage);}


                studentUserEntity = studentUserEntity.toBuilder().profileImage(pDTO.profileImage()).build();
                studentUserRepository.save(studentUserEntity);
            } else {
                teacherUserEntity = teacherUserEntity.toBuilder().profileImage(pDTO.profileImage()).build();
                teacherUserRepository.save(teacherUserEntity);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDTO.databaseError();
        }

        return PatchProfileImageResponseDTO.success();
    }

    @Override
    public ResponseEntity<? super DeleteUserResponseDTO> deleteUser(String userType, String userId) {
        try{
            if(userType.equals(UserType.STUDENT.getValue())){
                StudentUserEntity userEntity = studentUserRepository.findByUserId(userId);
                if(userEntity == null){
                    return DeleteUserResponseDTO.notExistUser();
                }
                List<FavoriteEntity> favoriteEntities = favoriteRepository.findAllByUserId(userId);
                if(favoriteEntities.size() != 0){
                    for(FavoriteEntity entity : favoriteEntities){
                        favoriteRepository.delete(entity);
                    }
                }
                List<CommentEntity> commentEntities = commentRepository.findAllByUserId(userId);
                if(commentEntities.size() != 0){
                    for(CommentEntity entity : commentEntities){
                        commentRepository.delete(entity);
                    }
                }
                List<BoardEntity> boardEntities = boardRepository.findAllByWriterId(userId);
                if(boardEntities.size() != 0){
                    for(BoardEntity entity : boardEntities){
                        boardService.deleteBoard(entity.getBoardNumber(), entity.getWriterId());
                    }
                }

                studentUserRepository.delete(userEntity);

            }else{
                List<FavoriteEntity> favoriteEntities = favoriteRepository.findAllByUserId(userId);
                if(favoriteEntities.size() != 0){
                    for(FavoriteEntity entity : favoriteEntities){
                        favoriteRepository.delete(entity);
                    }
                }
                List<CommentEntity> commentEntities = commentRepository.findAllByUserId(userId);
                if(commentEntities.size() != 0){
                    for(CommentEntity entity : commentEntities){
                        commentRepository.delete(entity);
                    }
                }
                TeacherUserEntity userEntity = teacherUserRepository.findByUserId(userId);
                if(userEntity == null){
                    return DeleteUserResponseDTO.notExistUser();
                }

                teacherUserRepository.delete(userEntity);
            }
        }catch (Exception e){
            e.printStackTrace();
            return ResponseDTO.databaseError();
        }
        return DeleteUserResponseDTO.success();
    }


    private void sendMail(MailDTO pDTO) {
        String message = pDTO.message();
        String title = pDTO.title();
        String address = pDTO.address();

        log.info("메일 전송");
        log.info(address);
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(address);
        mail.setSubject(title);
        log.info("message : " + message);
        mail.setText(message);
        mail.setFrom(from);
        mail.setReplyTo(from);
        javaMailSender.send(mail);
    }

    private String getTempPassword() {
        char[] charSet = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F',
                'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
        String str = "";

        int idx = 0;
        for (int i = 0; i < 10; i++) {
            idx = (int) (charSet.length * Math.random());
            str += charSet[idx];
        }
        return str;
    }

    private String generateAuthNumber() {
        return String.valueOf(ThreadLocalRandom.current().nextInt(100000, 1000000));
    }
}
