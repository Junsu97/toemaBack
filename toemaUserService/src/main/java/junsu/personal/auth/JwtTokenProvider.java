//package junsu.personal.auth;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import io.jsonwebtoken.io.Decoders;
//import io.jsonwebtoken.security.Keys;
//import jakarta.servlet.http.Cookie;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.ws.rs.core.HttpHeaders;
//import junsu.personal.dto.TokenDTO;
//import junsu.personal.util.CmmUtil;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
////import org.springframework.cloud.context.config.annotation.RefreshScope;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//import org.springframework.util.StringUtils;
//
//import javax.crypto.SecretKey;
//import java.util.Date;
//
//@Slf4j
//@Component
//@RequiredArgsConstructor
////@RefreshScope
//public class JwtTokenProvider {
//    @Value("$(jwt.secret.key)")
//    private String secretKey;
//    @Value("$(jwt.token.creator)")
//    private String creator;
//    @Value("$(jwt.token.access.valid.time)")
//    private long accessTokenValidTime;
//    @Value("$(jwt.token.access.name)")
//    private String accessTokenName;
//    @Value("$(jwt.token.refresh.valid.time)")
//    private long refreshTokenValidTime;
//    @Value("$(jwt.token.refresh.name)")
//    private String refreshTokenName;
//
//    /*
//    * Bearer Token Authentication
//    * 헤더에 토큰을 포함아여 전송 Authorization 헤더에 포함하여 전송한다
//    * JWT 토큰을 사용하여 인증하고
//    * 간단한 방식, 상태를 유지하지 않음, 확장성이 높음 등의 장점을 가짐
//    * 단점으로는 토큰 노출 위험이 있고, 토큰 관리등..
//    * */
//    public static final String HEADER_PREFIX = "Bearer";
//
//    /**
//     * JWT 토큰(Access Token, Refresh Token)생성
//     * @param userId    회원 아이디(ex. junsu0000)
//     * @param roles     회원 권한
//     * @param tokenType token 유형
//     * @return  인증 처리한 정보(로그인 성공, 실패)
//     */
//    public String createToken(String userId, String roles, JwtTokenType tokenType){
//        log.info(this.getClass().getName() + ".createToken Start!!!!!!!");
//        log.info("userId : " + userId);
//
//        long validTime = 0;
//
//        if(tokenType == JwtTokenType.ACCESS_TOKEN){
//            validTime = (accessTokenValidTime);
//        }else if(tokenType == JwtTokenType.REFRESH_TOKEN){
//            validTime = (refreshTokenValidTime);
//        }
//
//        Claims claims = Jwts.claims()
//                        .setIssuer(creator)  // 토큰 생성자 기입
//                        .setSubject(userId); // 회원아이디 저장 : PK 저장(userId)
//
//        claims.put("roles", roles);
//        Date now = new Date();
//
//        log.info(this.getClass().getName() + ".createToken End!!!!!!!");
//
//        SecretKey secret = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
//
//        return Jwts.builder()
//                .setClaims(claims) // 정보 저장
//                .setIssuedAt(now) // 토큰 발행시간 정보
//                .setExpiration(new Date(now.getTime() + (validTime * 1000))) // set Expire Time
//                .signWith(secret, SignatureAlgorithm.HS256) // 사용할 암호화 알고리즘
//                .compact();
//    }
//
//    /**
//     * JWT 토큰(Access Token, Refresh Token)에 저장된 값 가져오기
//     * @param token 토큰
//     * @return 회원아이디
//     */
//    public TokenDTO getTokenInfo(String token){
//        log.info(this.getClass().getName() + ".getTokenInfo Start!!!");
//
//        // 보안키 문자 JWT Key 형태로 변환
//        SecretKey secret = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
//
//        // JWT 토큰 정보
//        Claims claims = Jwts.parserBuilder().setSigningKey(secret).build().parseClaimsJws(token).getBody();
//
//        String userId = CmmUtil.nvl(claims.getSubject());
//        String role = CmmUtil.nvl((String) claims.get("roles")); // LoginService 생성된 토큰의 권한명과 동일
//
//        log.info("userId : " + userId);
//        log.info("role : " + role);
//
//        // TokenDTO는 자바17의 Record 객체 사용했기에 빌더 패턴 적용
//        TokenDTO rDTO = TokenDTO.builder().userId(userId).role(role).build();
//
//
//        log.info(this.getClass().getName() + ".getTokenInfo End!!!");
//
//        return rDTO;
//    }
//
//    public String resolveToken(HttpServletRequest request, JwtTokenType tokenType){
//        log.info(this.getClass().getName() + ".resolveToken Start!!!!!");
//
//        String tokenName = "";
//
//        if(tokenType == JwtTokenType.ACCESS_TOKEN){
//            tokenName = accessTokenName;
//        }else if(tokenType == JwtTokenType.REFRESH_TOKEN){
//            tokenName = refreshTokenName;
//        }
//
//        String token = "";
//        Cookie[] cookies = request.getCookies();
//        // Cookie에 저장된 데이터 모두 가져오기
//        if(cookies != null){
//            for(Cookie key : request.getCookies()){
//                log.info("cookie 이름 : " + key.getName());
//                if(key.getName().equals(tokenName)){
//                    token = CmmUtil.nvl(key.getValue());
//                    break;
//                }
//            }
//        }
//
//        //  쿠키에 토큰 없으면, Bearer 토큰에 값이 있는지 확인
//        if(token.length() == 0){
//            String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);
//
//            log.info("bearerToken : " + bearerToken);
//            if(StringUtils.hasText(bearerToken) && bearerToken.startsWith(HEADER_PREFIX)){
//                token = bearerToken.substring(7);
//            }
//
//            log.info("bearerToken token : " + token);
//        }
//
//        log.info(this.getClass().getName() + ".resolveToken End!!!!!");
//        return token;
//    }
//}
