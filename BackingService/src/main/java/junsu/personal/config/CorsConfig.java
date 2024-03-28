package junsu.personal.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry
                .addMapping("/**") // 모든 매핑에 대해서 허용
                .allowedMethods("*") // 모든 메소드에 대해서 허용
                .allowedOrigins("*"); // 모든 출처로부터 허용

    }
}
