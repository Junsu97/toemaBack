package junsu.personal.repository;

import junsu.personal.entity.StudentUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentUserRepository extends JpaRepository<StudentUserEntity, String> {

    boolean existsByUserId(String userId);

    boolean existsByNickname(String nickname);
    boolean existsByTelNumber(String telNumber);
    boolean existsByEmail(String email);
}
