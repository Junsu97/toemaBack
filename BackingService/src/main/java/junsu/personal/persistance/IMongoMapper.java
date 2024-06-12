package junsu.personal.persistance;

import junsu.personal.dto.request.auth.faceId.PostFaceIDRequestDTO;
import junsu.personal.dto.request.auth.faceId.PostFaceIdSignInRequestDTO;
import junsu.personal.dto.request.chat.ChatMessageRequest;

import java.util.List;

public interface IMongoMapper {

    int insertFaceId(PostFaceIDRequestDTO pDTO) throws Exception;
}
