package junsu.personal.service.impl;

import junsu.personal.dto.object.ChatMessageListDTO;
import junsu.personal.dto.request.chat.ChatMessageRequest;
import junsu.personal.dto.response.ResponseDTO;
import junsu.personal.dto.response.chat.PostChatResponseDTO;
import junsu.personal.entity.domain.ChatMessageDomain;
import junsu.personal.repository.StudentUserRepository;
import junsu.personal.repository.TeacherUserRepository;
import junsu.personal.repository.mongo.MongoChatMessageRepository;
import junsu.personal.service.IChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatService implements IChatService {
    private final StudentUserRepository studentUserRepository;
    private final TeacherUserRepository teacherUserRepository;
    private final MongoChatMessageRepository chatMessageRepository;

    @Override
    public ResponseEntity<? super PostChatResponseDTO> postChat(ChatMessageListDTO pDTO, String userId, String roomName) {
        try{
            if(!pDTO.userId().equals(userId)){
                return PostChatResponseDTO.noPermission();
            }

            boolean isExistUser = studentUserRepository.existsByUserId(pDTO.userId()) || teacherUserRepository.existsByUserId(pDTO.userId());

            if(!isExistUser) return PostChatResponseDTO.noExistUser();

            ChatMessageDomain domain = new ChatMessageDomain(pDTO, roomName);

            chatMessageRepository.save(domain);

        }catch (Exception e){
            ResponseDTO.databaseError();
        }

        return PostChatResponseDTO.success();
    }
}
