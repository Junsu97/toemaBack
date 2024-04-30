package junsu.personal.service.impl;

import junsu.personal.auth.UserType;
import junsu.personal.dto.request.user.PatchNicknameRequestDTO;
import junsu.personal.dto.request.user.PatchAndPostPasswordRequestDTO;
import junsu.personal.dto.request.user.PatchProfileImageRequestDTO;
import junsu.personal.dto.request.user.PostFindUserIdRequestDTO;
import junsu.personal.dto.response.ResponseDTO;
import junsu.personal.dto.response.auth.SignUpResponseDTO;
import junsu.personal.dto.response.user.*;
import junsu.personal.entity.StudentUserEntity;
import junsu.personal.entity.TeacherUserEntity;
import junsu.personal.repository.ImageRepository;
import junsu.personal.repository.StudentUserRepository;
import junsu.personal.repository.TeacherUserRepository;
import junsu.personal.service.IFileService;
import junsu.personal.service.IUserService;
import junsu.personal.util.EncryptUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService implements IUserService {
    private final StudentUserRepository studentUserRepository;
    private final TeacherUserRepository teacherUserRepository;
    private final IFileService fileService;
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
    public ResponseEntity<? super GetSignInUserResponseDTO> getSignInUser(String userId) {
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
            return GetSignInUserResponseDTO.success(studentUserEntity);
        } else {
            return GetSignInUserResponseDTO.success(teacherUserEntity);
        }

    }

    @Override
    public ResponseEntity<? super PostPasswordCheckResponseDTO> postPasswordCheck(PatchAndPostPasswordRequestDTO pDTO, String userId) {
        StudentUserEntity studentUserEntity = null;
        TeacherUserEntity teacherUserEntity = null;

        try {
            studentUserEntity = studentUserRepository.findByUserId(userId);
            teacherUserEntity = teacherUserRepository.findByUserId(userId);
            if (studentUserEntity == null && teacherUserEntity == null) return PatchNicknameResponseDTO.notExistUser();
            String userType = pDTO.userType();
            String password = pDTO.password();
            String encodedPassword = null;
            if (userType.equalsIgnoreCase(UserType.STUDENT.getValue())) {
                encodedPassword = studentUserEntity.getPassword();

            } else {
                encodedPassword = teacherUserEntity.getPassword();
            }
            boolean isMatched = passwordEncoder.matches(password, encodedPassword);
            if (!isMatched) return PostPasswordCheckResponseDTO.validationFailed();

        } catch (Exception e) {
            e.printStackTrace();
            ResponseDTO.databaseError();
        }

        return PostPasswordCheckResponseDTO.success();
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
    public ResponseEntity<? super PatchPasswordResponseDTO> patchPassword(PatchAndPostPasswordRequestDTO pDTO, String userId) {
        StudentUserEntity studentUserEntity = null;
        TeacherUserEntity teacherUserEntity = null;
        try {
            studentUserEntity = studentUserRepository.findByUserId(userId);
            teacherUserEntity = teacherUserRepository.findByUserId(userId);
            if (studentUserEntity == null && teacherUserEntity == null) return PatchPasswordResponseDTO.notExistUser();
            String userType = pDTO.userType();
            String password = pDTO.password();


            if (userType.equalsIgnoreCase(UserType.STUDENT.getValue())) {
                studentUserEntity = studentUserEntity.toBuilder().password(passwordEncoder.encode(password)).build();
                studentUserRepository.save(studentUserEntity);
            } else {
                teacherUserEntity = teacherUserEntity.toBuilder().password(passwordEncoder.encode(password)).build();
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
}
