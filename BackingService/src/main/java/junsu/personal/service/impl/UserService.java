package junsu.personal.service.impl;

import junsu.personal.dto.response.ResponseDTO;
import junsu.personal.dto.response.user.GetSignInUserResponseDTO;
import junsu.personal.entity.StudentUserEntity;
import junsu.personal.entity.TeacherUserEntity;
import junsu.personal.repository.StudentUserRepository;
import junsu.personal.repository.TeacherUserRepository;
import junsu.personal.service.IUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final StudentUserRepository studentUserRepository;
    private final TeacherUserRepository teacherUserRepository;

    @Override
    public ResponseEntity<? super GetSignInUserResponseDTO> getSignInUser(String userId) {
        log.info(this.getClass().getName() + ".getSignInUser Start!!!!!!!");
        StudentUserEntity studentUserEntity = null;
        TeacherUserEntity teacherUserEntity = null;
        try {
            studentUserEntity = studentUserRepository.findByUserId(userId);
            teacherUserEntity = teacherUserRepository.findByUserId(userId);

            if (studentUserEntity == null && teacherUserEntity == null) return GetSignInUserResponseDTO.notExistUser();

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDTO.databaseError();
        } finally {
            log.info(this.getClass().getName() + ".getSignInUser End!!!!!!!");
        }

        if (studentUserEntity != null && teacherUserEntity == null) {
            return GetSignInUserResponseDTO.success(studentUserEntity);
        } else {
            return GetSignInUserResponseDTO.success(teacherUserEntity);
        }

    }
}