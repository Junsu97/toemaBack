package junsu.personal.repository.mongo;

import junsu.personal.entity.domain.StudentFaceIdDomain;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MongoStudentFaceIdRepository extends MongoRepository<StudentFaceIdDomain, String> {
    StudentFaceIdDomain findByUserId(String userId);
}
