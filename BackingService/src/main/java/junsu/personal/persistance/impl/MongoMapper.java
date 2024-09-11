package junsu.personal.persistance.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.MongoCollection;
import junsu.personal.auth.UserType;
import junsu.personal.dto.request.auth.faceId.PostFaceIDRequestDTO;
import junsu.personal.entity.domain.LoginHistoryDomain;
import junsu.personal.persistance.AbstractMongoDBCommon;
import junsu.personal.persistance.IMongoMapper;
import junsu.personal.repository.mongo.object.LoginHistory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Component
public class MongoMapper extends AbstractMongoDBCommon implements IMongoMapper {
    private final MongoTemplate mongodb;
    private static final String LOGIN_HISTORY_COLLECTION = "loginHistory";
    @Override
    public int insertFaceId(PostFaceIDRequestDTO pDTO) throws Exception {
        log.info(this.getClass().getName() + ".insertFaceId Start!!!");
        int res = 0;
        String colName = pDTO.userType().equals(UserType.STUDENT.getValue())? "STUDENT" : "TEACHER";

        MongoCollection<Document> col = mongodb.getCollection(colName);
        col.insertOne(new Document(new ObjectMapper().convertValue(pDTO, Map.class)));

        res = 1;

        log.info(this.getClass().getName() + ".insertFaceId End!!!");
        return res;
    }

    @Override
    public int insertLoginHistory(LoginHistoryDomain history) {
        log.info(this.getClass().getName() + ".insertLoginHistory Start!!!");
        int res = 0;
        MongoCollection<Document> col = mongodb.getCollection(LOGIN_HISTORY_COLLECTION);

        // userId로 기존 도큐먼트가 있는지 확인
        Document existingDocument = col.find(new Document("userId", history.getUserId())).first();

        // Optional로 loginHistoryList 체크
        Optional<List<LoginHistory>> optionalLoginHistoryList = Optional.ofNullable(history.getLoginHistoryList());

        // loginHistoryList가 비어있지 않은 경우에만 처리
        if (optionalLoginHistoryList.isPresent() && !optionalLoginHistoryList.get().isEmpty()) {
            // 첫 번째 로그인 기록을 Optional로 처리
            Optional<LoginHistory> latestLoginHistory = optionalLoginHistoryList.map(list -> list.get(0));

            latestLoginHistory.ifPresent(loginHistory -> {
                // 기존 도큐먼트가 있을 경우 loginHistoryList에 기록 추가
                if (existingDocument != null) {
                    List<Document> existingHistoryList = (List<Document>) existingDocument.get("loginHistoryList");
                    boolean isDuplicate = existingHistoryList.stream()
                            .anyMatch(doc -> doc.getString("timeStamp").equals(loginHistory.timeStamp()));

                    if(!isDuplicate){
                        Document newLoginHistory = new Document("timeStamp", loginHistory.timeStamp());

                        col.updateOne(
                                new Document("userId", history.getUserId()),
                                new Document("$push", new Document("loginHistoryList", newLoginHistory))
                        );
                    }

                } else {
                    // 기존 도큐먼트가 없을 경우 새로 생성
                    col.insertOne(new Document(new ObjectMapper().convertValue(history, Map.class)));
                }
            });
        } else {
            log.warn("LoginHistoryList is null or empty, skipping insertion.");
        }

        res = 1;
        log.info(this.getClass().getName() + ".insertLoginHistory End!!!");
        return res;
    }




}
