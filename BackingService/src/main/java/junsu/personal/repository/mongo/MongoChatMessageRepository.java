package junsu.personal.repository.mongo;

import junsu.personal.entity.domain.ChatMessageDomain;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Date;
import java.util.List;

public interface MongoChatMessageRepository extends MongoRepository<ChatMessageDomain, String> {
    List<ChatMessageDomain> findByTimestampAfter(Date timestamp);
}
