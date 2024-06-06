package junsu.personal.repository;

import junsu.personal.entity.TutoringEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TutoringRepository extends JpaRepository<TutoringEntity, Long> {
    TutoringEntity findBySeq(Long seq);
    TutoringEntity findByStudentIdAndTutoringDate(String studentId, String tutoringDate);

    Boolean existsByStudentId(String userId);

    Boolean existsByTeacherId(String userId);

    List<TutoringEntity> findByTeacherIdAndStudentIdOrderByTutoringDateAscTutoringTimeAsc(String teacherId, String studentId);

    List<TutoringEntity> findByStudentIdOrderByTutoringDateAscTutoringTimeAsc(String userId);
}
