package junsu.personal.entity;

import jakarta.persistence.*;
import junsu.personal.dto.request.chat.PostChatRoomRequest;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "CHAT_ROOM")
@Table(name="CHAT_ROOM")
@DynamicInsert
@DynamicUpdate
@Builder
@Slf4j
public class ChatRoomEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="SEQ")
    private Long seq;

    @NonNull
    @Column(name = "ROOM_NAME")
    private String roomName;

    public ChatRoomEntity(PostChatRoomRequest request){
        this.roomName = request.roomName();
    }
}
