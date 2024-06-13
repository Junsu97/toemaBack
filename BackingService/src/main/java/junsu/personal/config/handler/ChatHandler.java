package junsu.personal.config.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import junsu.personal.dto.object.ChatMessageListDTO;
import junsu.personal.entity.domain.ChatMessageDomain;
import junsu.personal.service.IChatService;
import junsu.personal.util.CmmUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
@RequiredArgsConstructor
@Slf4j
public class ChatHandler extends TextWebSocketHandler {

    @Value("${jwt.secret.key}")
    private String secretKey;

    private static Set<WebSocketSession> clients = Collections.synchronizedSet(new LinkedHashSet<>());
    public static Map<String, String> roomInfo = Collections.synchronizedMap(new LinkedHashMap<>());
    private final IChatService chatService;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info("Connection established: {}", session.getId());
        String jwt = getQueryParam(session.getUri(), "token");
        if (jwt != null && validateToken(jwt)) {
            String roomName = CmmUtil.nvl((String) session.getAttributes().get("roomName"));
            String userId = CmmUtil.nvl((String) session.getAttributes().get("userId"));
            String roomNameHash = CmmUtil.nvl((String) session.getAttributes().get("roomNameHash"));

            log.info("roomName: {}, userId: {}, roomNameHash: {}", roomName, userId, roomNameHash);

            if (!clients.contains(session)) {
                clients.add(session);
                roomInfo.put(roomName, roomNameHash);
            }else{
                String d = roomInfo.get(roomName);
                log.info("ChatHandler roomInfo : " + d);
            }

            broadcastMessage(roomNameHash, userId + "님이 채팅방에 입장하셨습니다.", "관리자");


            List<ChatMessageDomain> recentMessage = chatService.getRecentMessage(roomName);
            ObjectMapper objectMapper = new ObjectMapper();

            for(ChatMessageDomain message : recentMessage){
                ChatMessageListDTO cDTO = ChatMessageListDTO.builder()
                        .userId(message.getUserId())
                        .timestamp(formatTimestamp(message.getTimestamp()))
                        .message(message.getMessage())
                        .build();

                String json = objectMapper.writeValueAsString(cDTO);
                session.sendMessage(new TextMessage(json));
            }

                log.info(roomInfo.keySet().toString());



        } else {
            session.close(CloseStatus.NOT_ACCEPTABLE);
            log.info("Not Valid Token");
        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        log.info("Message received from: {}", session.getId());
        String jwt = getQueryParam(session.getUri(), "token");
        if (jwt != null && validateToken(jwt)) {
            String roomName = CmmUtil.nvl((String) session.getAttributes().get("roomName"));
            String userId = CmmUtil.nvl((String) session.getAttributes().get("userId"));
            String roomNameHash = CmmUtil.nvl((String) session.getAttributes().get("roomNameHash"));

            log.info("roomName: {}, userId: {}, roomNameHash: {}", roomName, userId, roomNameHash);

            String msg = CmmUtil.nvl(message.getPayload());
            log.info("Message payload: {}", msg);

            ChatMessageListDTO cDTO = new ObjectMapper().readValue(msg, ChatMessageListDTO.class);
            cDTO = cDTO.toBuilder().timestamp(formatTimestamp(new Date())).build();

            broadcastMessage(roomNameHash, cDTO, userId, roomName);

        } else {
            session.close(CloseStatus.NOT_ACCEPTABLE);
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        log.info("Connection closed: {}", session.getId());
        String jwt = getQueryParam(session.getUri(), "token");
        if (jwt != null && validateToken(jwt)) {
            String roomName = CmmUtil.nvl((String) session.getAttributes().get("roomName"));
            String userId = CmmUtil.nvl((String) session.getAttributes().get("userId"));
            String roomNameHash = CmmUtil.nvl((String) session.getAttributes().get("roomNameHash"));

            log.info("roomName: {}, userId: {}, roomNameHash: {}", roomName, userId, roomNameHash);

            clients.remove(session);

            broadcastMessage(roomNameHash, userId + "님이 채팅방을 떠났습니다.", "관리자");

        } else {
            session.close(CloseStatus.NOT_ACCEPTABLE);
        }
    }

    private void broadcastMessage(String roomNameHash, String message, String sender) {
        ChatMessageListDTO cDTO = ChatMessageListDTO.builder()
                .userId(sender)
                .message(message)
                .timestamp(formatTimestamp(new Date()))
                .build();

        try {
            String json = new ObjectMapper().writeValueAsString(cDTO);
            log.info("Broadcasting message: {}", json);

            clients.forEach(s -> {
                if (roomNameHash.equals(s.getAttributes().get("roomNameHash"))) {
                    try {
                        s.sendMessage(new TextMessage(json));
                    } catch (Exception e) {
                        log.error("Error sending message: ", e);
                    }
                }
            });
        } catch (Exception e) {
            log.error("Error broadcasting message: ", e);
        }
    }

    private void broadcastMessage(String roomNameHash, ChatMessageListDTO cDTO, String userId, String roomName) {
        try {
            String json = new ObjectMapper().writeValueAsString(cDTO);
            log.info("Broadcasting message: {}", json);

            clients.forEach(s -> {
                if (roomNameHash.equals(s.getAttributes().get("roomNameHash"))) {
                    try {
                        s.sendMessage(new TextMessage(json));
                        chatService.postChat(cDTO, userId, roomName);
                    } catch (Exception e) {
                        log.error("Error sending message: ", e);
                    }
                }
            });
        } catch (Exception e) {
            log.error("Error broadcasting message: ", e);
        }
    }

    private String formatTimestamp(Date timestamp) {
        if (timestamp == null) {
            return null;
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formatter.format(timestamp);
    }

    private boolean validateToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private String getQueryParam(URI uri, String paramName) {
        if (uri == null || paramName == null || paramName.isEmpty()) {
            return null;
        }
        String query = uri.getQuery();
        if (query == null || query.isEmpty()) {
            return null;
        }
        String[] pairs = query.split("&");
        for (String pair : pairs) {
            String[] keyValue = pair.split("=");
            if (keyValue.length == 2 && keyValue[0].equals(paramName)) {
                return keyValue[1];
            }
        }
        return null;
    }
}
