package junsu.personal.repository;

import junsu.personal.entity.MatchEntity;
import junsu.personal.entity.StudentUserEntity;
import junsu.personal.entity.primaryKey.MatchAndHomeworkPk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatchRepository extends JpaRepository<MatchEntity, MatchAndHomeworkPk> {
    List<MatchEntity> findByStudentIdOrderByWriteDatetimeDesc(String userId);
    List<MatchEntity> findByTeacherIdOrderByWriteDatetimeDesc(String userId);
    MatchEntity findByStudentId(String userId);
    MatchEntity findByTeacherId(String userId);
    MatchEntity findByTeacherIdAndStudentId(String teacherId, String studentId);
    MatchEntity findByStudentIdAndStatus(String userId, String status);

}
