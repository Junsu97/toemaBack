package junsu.personal.service.impl;

import junsu.personal.dto.object.ChatMessageListDTO;
import junsu.personal.dto.request.chat.PostChatRoomRequest;
import junsu.personal.dto.response.ResponseDTO;
import junsu.personal.dto.response.chat.PostChatResponseDTO;
import junsu.personal.dto.response.chat.PostChatRoomResponseDTO;
import junsu.personal.entity.ChatRoomEntity;
import junsu.personal.entity.domain.ChatMessageDomain;
import junsu.personal.repository.ChatRoomRepository;
import junsu.personal.repository.StudentUserRepository;
import junsu.personal.repository.TeacherUserRepository;
import junsu.personal.repository.mongo.MongoChatMessageRepository;
import junsu.personal.service.IChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatService implements IChatService {
    private final StudentUserRepository studentUserRepository;
    private final TeacherUserRepository teacherUserRepository;
    private final MongoChatMessageRepository chatMessageRepository;
    private final ChatRoomRepository chatRoomRepository;

    @Override
    public void postChat(ChatMessageListDTO pDTO, String userId, String roomName) {
        log.info(this.getClass().getName() + ".postChat start!!!");
        try {
            if (!pDTO.userId().equals(userId)) {
                log.warn("Permission denied for user: {}", userId);
                return;
            }

            boolean isExistUser = studentUserRepository.existsByUserId(pDTO.userId()) || teacherUserRepository.existsByUserId(pDTO.userId());

            if (!isExistUser) {
                log.warn("User does not exist: {}", pDTO.userId());
                return;
            }

            ChatMessageDomain domain = new ChatMessageDomain(pDTO, roomName);

            chatMessageRepository.save(domain);
            log.info("Message saved successfully for user: {} in room: {}", userId, roomName);

        } catch (Exception e) {
            log.error("Database error occurred while saving chat message: ", e);
        }finally {
            log.info(this.getClass().getName() + ".postChat end!!!");
        }

    }

    @Override
    public ResponseEntity<? super PostChatRoomResponseDTO> postChatRoom(PostChatRoomRequest request, String userId) {
        try{
            boolean isExistUser = studentUserRepository.existsByUserId(userId) || teacherUserRepository.existsByUserId(userId);

            if (!isExistUser) {return PostChatRoomResponseDTO.noExistUser();}

            ChatRoomEntity entity = new ChatRoomEntity(request);
            chatRoomRepository.save(entity);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseDTO.databaseError();
        }
        return PostChatRoomResponseDTO.success();
    }

    @Override
    public List<ChatMessageDomain> getRecentMessage(String roomName) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, -1);

        Date oneHourAge = calendar.getTime();

        return chatMessageRepository.findByRoomNameAndTimestampAfter(roomName, oneHourAge);
    }
}
