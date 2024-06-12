package junsu.personal.config.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import junsu.personal.dto.object.ChatMessageListDTO;
import junsu.personal.dto.request.chat.ChatMessageRequest;
import junsu.personal.util.CmmUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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
    private final String secretKey;

    private static Set<WebSocketSession> clients = Collections.synchronizedSet(new LinkedHashSet<>());
    private static Map<String, String> roomInfo = Collections.synchronizedMap(new LinkedHashMap<>());

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info(this.getClass().getName() + ".afterConnectionEstablished start!!!");
        String jwt = this.getQueryParam(session.getUri(), "token");
        if (jwt != null && validateToken(jwt)) {
            String roomName = CmmUtil.nvl((String) session.getAttributes().get("roomName"));
            String userId = CmmUtil.nvl((String) session.getAttributes().get("userId"));
            String roomNameHash = CmmUtil.nvl((String) session.getAttributes().get("roomNameHash"));

            log.info("roomName : " + roomName);
            log.info("userId : " + userId);
            log.info("roomNameHash : " + roomNameHash);

            if(!clients.contains(session)){
                clients.add(session);
                roomInfo.put(roomName, roomNameHash);
            }

            clients.forEach(s -> {
                if (roomNameHash.equals(s.getAttributes().get("roomNameHash"))) {
                    try {
                        ChatMessageListDTO cDTO = ChatMessageListDTO.builder()
                                .userId("관리자")
                                .message(userId + "님이 채팅방에 입장하셨습니다.")
                                .timestamp(formatTimestamp(new Date()))
                                .build();

                        String json = new ObjectMapper().writeValueAsString(cDTO);
                        log.info("json : " + json);

                        TextMessage chatMsg = new TextMessage(json);
                        s.sendMessage(chatMsg);

                        cDTO = null;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

        } else {
            session.close(CloseStatus.NOT_ACCEPTABLE);
        }


        log.info(this.getClass().getName() + ".afterConnectionEstablished end!!!");
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        log.info(this.getClass().getName() + ".handleTextMessage start!!!");
        String jwt = this.getQueryParam(session.getUri(), "token");
        if (jwt != null && validateToken(jwt)) {
            String roomName = CmmUtil.nvl((String) session.getAttributes().get("roomName"));
            String userId = CmmUtil.nvl((String) session.getAttributes().get("userId"));
            String roomNameHash = CmmUtil.nvl((String) session.getAttributes().get("roomNameHash"));

            log.info("roomName : " + roomName);
            log.info("userId : " + userId);
            log.info("roomNameHash : " + roomNameHash);

            String msg = CmmUtil.nvl(message.getPayload());
            log.info("msg : msg");

            ChatMessageListDTO cDTO = new ObjectMapper().readValue(msg, ChatMessageListDTO.class);

            cDTO.toBuilder().timestamp(formatTimestamp(new Date())).build();

            String json = new ObjectMapper().writeValueAsString(cDTO);

            log.info("json : " + json);

            clients.forEach(s -> {
                if (roomNameHash.equals(s.getAttributes().get("roomNameHash"))) {
                    try {
                        TextMessage chatMsg = new TextMessage(json);
                        s.sendMessage(chatMsg);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }else{
            session.close(CloseStatus.NOT_ACCEPTABLE);
        }
        log.info(this.getClass().getName() + ".handleTextMessage end!!!");
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        log.info(this.getClass().getName() + ".afterConnectionClosed start!!!");
        String jwt = this.getQueryParam(session.getUri(), "token");
        if (jwt != null && validateToken(jwt)) {
            String roomName = CmmUtil.nvl((String) session.getAttributes().get("roomName"));
            String userId = CmmUtil.nvl((String) session.getAttributes().get("userId"));
            String roomNameHash = CmmUtil.nvl((String) session.getAttributes().get("roomNameHash"));

            log.info("roomName : " + roomName);
            log.info("userId : " + userId);
            log.info("roomNameHash : " + roomNameHash);

            clients.remove(session);

            clients.forEach(s -> {
                if (roomNameHash.equals(s.getAttributes().get("roomNameHash"))) {
                    try {
                        ChatMessageListDTO cDTO = ChatMessageListDTO.builder()
                                .userId("관리자")
                                .message(userId + "님이 채팅방을 떠났습니다.")
                                .timestamp(formatTimestamp(new Date()))
                                .build();

                        String json = new ObjectMapper().writeValueAsString(cDTO);
                        log.info("json : " + json);

                        TextMessage chatMsg = new TextMessage(json);
                        s.sendMessage(chatMsg);

                        cDTO = null;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } else{
            session.close(CloseStatus.NOT_ACCEPTABLE);
        }
        log.info(this.getClass().getName() + ".afterConnectionClosed end!!!");
    }

    private static String formatTimestamp(Date timestamp) {
        if (timestamp == null) {
            return null; // 또는 기본값을 반환하도록 설정할 수 있습니다.
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
            return true; // 추가 검증 로직 필요 시 여기에 추가
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
