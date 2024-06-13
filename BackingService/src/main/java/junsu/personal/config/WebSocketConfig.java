package junsu.personal.config;

import junsu.personal.config.handler.ChatHandler;
import junsu.personal.config.interceptors.BeforeInterceptor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocket
@RequiredArgsConstructor
@Slf4j
public class WebSocketConfig implements WebSocketConfigurer {
    private final ChatHandler chatHandler;
    private final BeforeInterceptor beforeInterceptor;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        log.info("WebSocket Execute!!");

        registry.addHandler(chatHandler, "/ws/{roomName}/{userId}")
                .setAllowedOrigins("*")
                .addInterceptors(beforeInterceptor);

    }
}
