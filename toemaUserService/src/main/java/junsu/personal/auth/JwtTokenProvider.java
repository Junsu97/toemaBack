package junsu.personal.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
//import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
//@RefreshScope
public class JwtTokenProvider {
    @Value("$(jwt.secret.key)")
    private String secretKey;
    @Value("$(jwt.token.creator)")
    private String creator;
    @Value("$(jwt.token.access.valid.time)")
    private long accessTokenValidTime;
    @Value("$(jwt.token.access.name)")
    private String accessTokenName;
    @Value("$(jwt.token.refresh.valid.time)")
    private long refreshTokenValidTime;
    @Value("$(jwt.token.refresh.name)")
    private String refreshTokenName;

    public static final String HEADER_PREFIX = "Bearer";

    /**
     * JWT 토큰(Access Token, Refresh Token)생성
     * @param userId    회원 아이디(ex. junsu0000)
     * @param roles     회원 권한
     * @param tokenType token 유형
     * @return  인증 처리한 정보(로그인 성공, 실패)
     */
    public String createToke(String userId, String roles, JwtTokenType tokenType){
        log.info(this.getClass().getName() + ".createToken Start!!!!!!!");
        log.info("userId : " + userId);

        long validTime = 0;

        if(tokenType == JwtTokenType.ACCESS_TOKEN){
            validTime = (accessTokenValidTime);
        }else if(tokenType == JwtTokenType.REFRESH_TOKEN){
            validTime = (refreshTokenValidTime);
        }

        Claims claims = Jwts.claims()
                        .setIssuer(creator)  // 토큰 생성자 기입
                        .setSubject(userId); // 회원아이디 저장 : PK 저장(userId)

        claims.put("roles", roles);
        Date now = new Date();

        log.info(this.getClass().getName() + ".createToken End!!!!!!!");

        SecretKey secret = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
        
        return Jwts.builder()
                .setClaims(claims) // 정보 저장
                .setIssuedAt(now) // 토큰 발행시간 정보
                .setExpiration(new Date(now.getTime() + (validTime * 1000))) // set Expire Time
                .signWith(secret, SignatureAlgorithm.HS256) // 사용할 암호화 알고리즘
                .compact();
    }
}
