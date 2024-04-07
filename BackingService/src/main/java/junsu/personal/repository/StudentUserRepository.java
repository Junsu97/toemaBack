package junsu.personal.repository;

import junsu.personal.entity.StudentUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentUserRepository extends JpaRepository<StudentUserEntity, String> {

}
