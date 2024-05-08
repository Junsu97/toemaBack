package junsu.personal.persistance.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.MongoCollection;
import junsu.personal.auth.UserType;
import junsu.personal.dto.request.auth.faceId.PostFaceIDRequestDTO;
import junsu.personal.dto.request.auth.faceId.PostFaceIdSignInRequestDTO;
import junsu.personal.persistance.AbstractMongoDBCommon;
import junsu.personal.persistance.IMongoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Component
public class MongoMapper extends AbstractMongoDBCommon implements IMongoMapper {
    private final MongoTemplate mongodb;

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
}
