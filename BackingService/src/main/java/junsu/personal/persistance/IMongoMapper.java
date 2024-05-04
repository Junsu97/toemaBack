package junsu.personal.persistance;

import junsu.personal.dto.request.faceId.PostFaceIDRequestDTO;

public interface IMongoMapper {

    int insertData(PostFaceIDRequestDTO pDTO, String colNm) throws Exception;
}
