package junsu.personal.persistance.impl;

import junsu.personal.dto.request.faceId.PostFaceIDRequestDTO;
import junsu.personal.persistance.AbstractMongoDBCommon;
import junsu.personal.persistance.IMongoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class MongoMapper extends AbstractMongoDBCommon implements IMongoMapper {
    private final MongoTemplate mongodb;
    @Override
    public int insertData(PostFaceIDRequestDTO pDTO, String colNm) throws Exception {
        log.info(this.getClass().getName() + ".insertData Start!!!");
        int res = 0;
        super.createCollection(mongodb, colNm);
        log.info(this.getClass().getName() + ".insertData End!!!");
    }
}
