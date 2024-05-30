package junsu.personal.repository;

import junsu.personal.entity.HomeworkEntity;
import junsu.personal.entity.primaryKey.MatchAndHomeworkPk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HomeworkRepository extends JpaRepository<HomeworkEntity, MatchAndHomeworkPk> {
    HomeworkEntity findByTeacherIdAndStudentId(String teacherId, String studentId);
}
