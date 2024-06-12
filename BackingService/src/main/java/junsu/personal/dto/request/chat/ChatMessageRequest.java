package junsu.personal.dto.request.chat;

import jakarta.validation.constraints.NotBlank;

public record ChatMessageRequest (
        @NotBlank
        String userId,
        @NotBlank
        String msg
){
}
