package junsu.personal.dto.object;

import jakarta.validation.constraints.NotBlank;
import junsu.personal.entity.domain.ChatMessageDomain;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Builder(toBuilder = true)
public record ChatMessageListDTO(
        @NotBlank
        String userId,
        String timestamp,
        @NotBlank
        String message
) {
    public ChatMessageListDTO(ChatMessageDomain domain){
        this(domain.getUserId(), formatTimestamp(domain.getTimestamp()), domain.getMessage());
    }
    private static String formatTimestamp(Date timestamp) {
        if (timestamp == null) {
            return null; // 또는 기본값을 반환하도록 설정할 수 있습니다.
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formatter.format(timestamp);
    }

    public static List<ChatMessageListDTO> getList(List<ChatMessageDomain> domains){
        List<ChatMessageListDTO> list = new ArrayList<>();
        for(ChatMessageDomain domain : domains){
            ChatMessageListDTO chatMessageListDTO = new ChatMessageListDTO(domain);
            list.add(chatMessageListDTO);
        }
        return list;
    }
}
