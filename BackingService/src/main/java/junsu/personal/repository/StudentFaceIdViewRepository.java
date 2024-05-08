package junsu.personal.repository;

import junsu.personal.entity.StudentUserFaceIdViewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentFaceIdViewRepository extends JpaRepository<StudentUserFaceIdViewEntity, String> {
    StudentUserFaceIdViewEntity findByUserId(String userId);
}
