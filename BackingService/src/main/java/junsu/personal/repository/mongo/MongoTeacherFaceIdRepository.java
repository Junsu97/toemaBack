package junsu.personal.repository.mongo;

import junsu.personal.entity.domain.TeacherFaceIdDomain;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MongoTeacherFaceIdRepository extends MongoRepository<TeacherFaceIdDomain, String> {
    TeacherFaceIdDomain findByUserId(String userId);
}
