package junsu.personal.service;

import junsu.personal.dto.object.ChatMessageListDTO;
import junsu.personal.dto.request.chat.ChatMessageRequest;
import junsu.personal.dto.response.chat.PostChatResponseDTO;
import org.springframework.http.ResponseEntity;

public interface IChatService {
    ResponseEntity<? super PostChatResponseDTO> postChat(ChatMessageListDTO pDTO, String userId, String roomName);
}
