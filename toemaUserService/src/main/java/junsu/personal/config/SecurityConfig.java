package junsu.personal.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Slf4j
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Value("${jwt.token.access.name}")
    private String accessTokenName;

    @Value("${jwt.token.refresh.name}")
    private String refreshTokenName;

    @Bean
    public PasswordEncoder passwordEncoder(){
        log.info(this.getClass().getName() + ".PasswrodEndcoder Start!!!");
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        log.info(this.getClass().getName() + ".filterChain Start!!!!");
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .formLogin(login -> login //로그인페이지 설정
                        .loginPage("/#/login")
                        .loginProcessingUrl("/login/loginProc")
                        .usernameParameter("user_id") // 로그인 ID로 사용할 html의 input 객체의 name값
                        .passwordParameter("password") // 로그인 패스워드로 사용할 html의 input 객체의 name 값

                        // 로그인 처리
                        .successForwardUrl("/#/login/loginSuccess")  // Web MVC, Controller 사용할 때 적용 / 로그인 성공 URL
                        .failureForwardUrl("/#/login/loginFail") // Web MVC, Controller 사용할 때 적용 / 로그인 실패 URL
                )
                .logout(logout -> logout
                        .logoutUrl("/#/user/logout")
                        .deleteCookies(accessTokenName, refreshTokenName)
                        .logoutSuccessUrl("/#/logout")
                )
                .sessionManagement(ss -> ss.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        log.info(this.getClass().getName() + ".filterChain End!!!!");
        return http.build();
    }
}
