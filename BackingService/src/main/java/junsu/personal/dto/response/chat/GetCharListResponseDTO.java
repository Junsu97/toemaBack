package junsu.personal.dto.response.chat;

import junsu.personal.common.ResponseCode;
import junsu.personal.common.ResponseMessage;
import junsu.personal.dto.object.ChatMessageListDTO;
import junsu.personal.dto.response.ResponseDTO;
import junsu.personal.entity.domain.ChatMessageDomain;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class GetCharListResponseDTO extends ResponseDTO {
    private List<ChatMessageListDTO> chatList;
    private GetCharListResponseDTO(List<ChatMessageDomain> domains){
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.chatList = ChatMessageListDTO.getList(domains);
    }

    public static ResponseEntity<GetCharListResponseDTO> success(List<ChatMessageDomain> domains){
        GetCharListResponseDTO result = new GetCharListResponseDTO(domains);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
