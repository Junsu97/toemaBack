package junsu.personal.repository;

import junsu.personal.entity.TeacherUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherUserRepository extends JpaRepository<TeacherUserEntity, String> {
    boolean existsByUserId(String userId);

    boolean existsByNickname(String nickname);
    boolean existsByTelNumber(String telNumber);
    boolean existsByEmail(String email);

    TeacherUserEntity findByUserId(String userId);
    TeacherUserEntity findByEmail(String email);
    TeacherUserEntity findByEmailAndUserName(String email, String userName);
    TeacherUserEntity findByUserIdAndEmailAndUserName(String userId, String email, String userName);
}
