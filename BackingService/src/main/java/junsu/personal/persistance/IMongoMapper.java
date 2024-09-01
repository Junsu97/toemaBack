package junsu.personal.persistance;

import junsu.personal.dto.request.auth.faceId.PostFaceIDRequestDTO;
import junsu.personal.entity.domain.LoginHistoryDomain;
import junsu.personal.repository.mongo.object.LoginHistory;

import java.util.List;

public interface IMongoMapper {

    int insertFaceId(PostFaceIDRequestDTO pDTO) throws Exception;
    int insertLoginHistory(LoginHistoryDomain history);
}
