package junsu.personal.entity.domain;

import jakarta.persistence.Id;
import junsu.personal.dto.object.ChatMessageListDTO;
import junsu.personal.dto.request.chat.ChatMessageRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;


import java.util.Date;

@Document(collection = "chatMessage")
@Getter
@NoArgsConstructor
public class ChatMessageDomain {
    @Id
    @Field(name = "_id")
    private String id;

    @Indexed
    @Field(name = "timestamp")
    private Date timestamp;

    @Field(name = "message")
    private String message;
    @Field(name = "roomName")
    private String roomName;

    @Field(name = "userId")
    private String userId;

    public ChatMessageDomain(ChatMessageListDTO pDTO, String roomName){
        this.userId = pDTO.userId();
        this.message = pDTO.message();
        this.roomName = roomName;
        this.timestamp = new Date();
    }
}
