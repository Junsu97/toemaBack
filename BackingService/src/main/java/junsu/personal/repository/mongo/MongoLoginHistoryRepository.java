package junsu.personal.repository.mongo;

import junsu.personal.entity.domain.LoginHistoryDomain;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MongoLoginHistoryRepository extends MongoRepository<LoginHistoryDomain, String> {
    LoginHistoryDomain findByUserId(String userId);
}
