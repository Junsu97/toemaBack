package junsu.personal.controller;

import jakarta.validation.Valid;
import junsu.personal.config.handler.ChatHandler;
import junsu.personal.dto.request.chat.PostChatRoomRequest;
import junsu.personal.dto.response.chat.PostChatRoomResponseDTO;
import junsu.personal.service.IChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@Slf4j
@RequiredArgsConstructor
public class ChatController {
    private final IChatService chatService;
    @RequestMapping(value = "roomList")
    @ResponseBody
    public List<String> roomList(){
        log.info(this.getClass().getName() + ".roomList Start!");
        log.info(this.getClass().getName() + ".roomList End!");

        List<String> result = new ArrayList<>(ChatHandler.roomInfo.keySet());

        log.info("size : "+result.size());
        for(String s : result){
            log.info(s);
        }


        return result;
    }

    @PostMapping("createRoom")
    @ResponseBody
    public ResponseEntity<? super PostChatRoomResponseDTO> postChatRoom(@RequestBody @Valid PostChatRoomRequest request,
                                                                        @AuthenticationPrincipal String userId){
        ResponseEntity<? super PostChatRoomResponseDTO> response = chatService.postChatRoom(request, userId);
        return response;
    }
}
