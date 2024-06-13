package junsu.personal.service;

import junsu.personal.dto.object.ChatMessageListDTO;
import junsu.personal.dto.request.chat.PostChatRoomRequest;
import junsu.personal.dto.response.chat.PostChatRoomResponseDTO;
import junsu.personal.entity.domain.ChatMessageDomain;
import org.springframework.http.ResponseEntity;


import java.util.List;

public interface IChatService {
    void postChat(ChatMessageListDTO pDTO, String userId, String roomName);
    ResponseEntity<? super PostChatRoomResponseDTO> postChatRoom(PostChatRoomRequest request, String userId);
    List<ChatMessageDomain> getRecentMessage(String roomName);
}
