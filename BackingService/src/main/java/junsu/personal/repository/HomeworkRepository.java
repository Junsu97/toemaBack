package junsu.personal.repository;

import junsu.personal.entity.HomeworkEntity;
import junsu.personal.entity.primaryKey.MatchAndHomeworkPk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HomeworkRepository extends JpaRepository<HomeworkEntity, Long> {
    HomeworkEntity findBySeq(Long seq);

    Boolean existsByTeacherId(String userId);
    Boolean existsByStudentId(String userId);
    List<HomeworkEntity> findByStudentIdOrderByStartDateAsc(String userId);
    List<HomeworkEntity> findByTeacherIdAndStudentIdOrderByStartDate(String teacherId, String studentId);
    List<HomeworkEntity> findByTeacherIdAndStudentIdAndStartDateOrderByStartDate(String teacherId, String studentId, String startDate);
}
