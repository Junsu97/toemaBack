package junsu.personal.repository.mongo;

import junsu.personal.entity.domain.ChatMessageDomain;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface MongoChatMessageRepository extends MongoRepository<ChatMessageDomain, String> {
    List<ChatMessageDomain> findByRoomNameAndTimestampAfter(String roomName, Date timestamp);
}
