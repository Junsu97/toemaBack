package junsu.personal.auth;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import junsu.personal.dto.TokenDTO;
import junsu.personal.util.CmmUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.antlr.v4.runtime.Token;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {
    @Value("${jwt.secret.key}")
    private String secretKey;

    @Value("${jwt.token.creator}")
    private String creator;

    @Value("${jwt.token.access.valid.time}")
    private long accessTokenTime;

    @Value("${jwt.token.access.name}")
    private String accessTokenName;

    @Value("{jwt.token.refresh.valid.time}")
    private long refreshTokenValidTime;

    @Value("${jwt.token.refresh.name}")
    private String refreshTokenName;

    public static final String HEADER_PREFIX = "Bearer";

    /**
     * JWT 토큰(Access Token, Refresh Token)생성
     *
     * @param userId    회원 아이디(ex. hglee67)
     * @param roles     회원 권한
     * @param tokenType token 유형
     * @return 인증 처리한 정보(로그인 성공, 실패)
     */
    public String createToken(String userId, String roles, JwtTokenType tokenType){
        log.info(this.getClass().getName() + ".createToken Start!!!");

        log.info("userId : " + userId);

        long validTime = 0;

        if(tokenType == JwtTokenType.ACCESS_TOKEN){
            validTime = (accessTokenTime);
        }else if(tokenType == JwtTokenType.REFRESH_TOKEN){
            validTime = (refreshTokenValidTime);
        }

         Claims claims = Jwts.claims()
                 .setIssuer(creator) // JWT 토큰 생성자 기입
                 .setSubject(userId); // 회원 아이디 저장 : PK

        claims.put("roles", roles);
        Date now = new Date();


        log.info(this.getClass().getName() + ".createToken End!!!");

        SecretKey secret  = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(secretKey));

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + (validTime * 1000)))
                .signWith(secret, SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * JWT 토큰(Access Token, Refresh Token)에 저장된 값 가져오기
     *
     * @param token 토큰
     * @return 회원 아이디(ex. hglee67)
     */
    public TokenDTO getTokenInfo(String token){
        log.info(this.getClass().getName() + ".getTokenInfo Start!!!");

        SecretKey secret = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(secretKey));

        Claims claims = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();

        String userId = CmmUtil.nvl(claims.getSubject());
        String role = CmmUtil.nvl((String) claims.get("roles"));

        log.info("userId : " + userId);
        log.info("role : " + role);

        TokenDTO rDTO = TokenDTO.builder().userId(userId).role(role).build();


        log.info(this.getClass().getName() + ".getTokenInfo End!!!");

        return rDTO;
    }

    public String resolveToken(HttpServletRequest request, JwtTokenType tokenType){
        log.info(this.getClass().getName() + ".resolveToken Start!!!");

        String tokenName = "";

        if(tokenType == JwtTokenType.ACCESS_TOKEN){
            tokenName = accessTokenName;
        }else if(tokenType == JwtTokenType.REFRESH_TOKEN){
            tokenName = refreshTokenName;
        }

        String token = "";

        Cookie[] cookies = request.getCookies();

        if(cookies != null){
            for(Cookie key : request.getCookies()){
                log.info("cookies 이름 : " + key.getName());
                if(key.getName().equals(tokenName)){
                    token = CmmUtil.nvl(key.getValue());
                    break;
                }
            }
        }

        if(token.length() == 0){
            String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);

            log.info("bearertoken : " + bearerToken);
            if(StringUtils.hasText(bearerToken) && bearerToken.startsWith(HEADER_PREFIX)){
                token = bearerToken.substring(7);
            }

            log.info("bearerToken token : "  + token);
        }

        log.info(this.getClass().getName() + ".resolveToken End!!!");
        return token;
    }


}
