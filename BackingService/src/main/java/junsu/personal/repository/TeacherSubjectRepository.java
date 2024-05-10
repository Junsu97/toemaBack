package junsu.personal.repository;


import junsu.personal.entity.TeacherSubjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherSubjectRepository extends JpaRepository<TeacherSubjectEntity, String>{
    TeacherSubjectEntity findByUserId(String userId);

}
