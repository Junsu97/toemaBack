package junsu.personal.dto.request.chat;

import jakarta.validation.constraints.NotBlank;

public record PostChatRoomRequest(
        @NotBlank
        String roomName
){
}
