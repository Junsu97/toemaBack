package junsu.personal.persistance;

import junsu.personal.dto.request.auth.faceId.PostFaceIDRequestDTO;

import java.util.List;

public interface IMongoMapper {

    int insertFaceId(PostFaceIDRequestDTO pDTO) throws Exception;
}
