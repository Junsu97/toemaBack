package junsu.personal.config.interceptors;


import junsu.personal.util.CmmUtil;
import junsu.personal.util.EncryptUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import java.util.Map;

@Slf4j
@Component
public class BeforeInterceptor extends HttpSessionHandshakeInterceptor {

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        String path = CmmUtil.nvl(request.getURI().getPath());
        log.info("path : " + path);

        String[] uriInfo = path.split("/");

        String roomName = CmmUtil.nvl(uriInfo[2]);
        String userName = CmmUtil.nvl(uriInfo[3]);

        String roomNameHash = EncryptUtil.encHashSHA256(roomName);

        log.info("roomName : " + roomName);
        log.info("userId : " + userName);
        log.info("roomNameHash : " + roomNameHash);

        attributes.put("roomName", roomName);
        attributes.put("userId", userName);
        attributes.put("roomNameHash", roomNameHash);

        return super.beforeHandshake(request, response, wsHandler, attributes);
    }
}
