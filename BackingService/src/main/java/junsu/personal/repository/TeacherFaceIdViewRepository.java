package junsu.personal.repository;

import junsu.personal.entity.TeacherUserFaceIdViewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherFaceIdViewRepository extends JpaRepository<TeacherUserFaceIdViewEntity, String> {
    TeacherUserFaceIdViewEntity findByUserId(String userId);
}
