package junsu.personal.provider;

import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtProvider {

    @Value("${jwt.secret.key}")
    private String secretKey;




    public String create(String userId, String roles, String userType){
        Date expiredDate = Date.from(Instant.now().plus(1, ChronoUnit.HOURS));
        Claims claims = Jwts.claims()
                .setSubject(userId);

        claims.put("roles", roles);
        claims.put("userType", userType);
        String jwt = Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .setIssuedAt(new Date())
                .setExpiration(expiredDate)
                .compact();

        return jwt;
    }


    public String validate(String jwt){
        Claims claims = null;

        try{
            claims = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(jwt)
                    .getBody();


        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

        return claims.getSubject();
    }

    public boolean validateAccessToken(String accessToken){
        if(accessToken == null){
            return false;
        }
        String bearerToken = accessToken.trim();

        if(!bearerToken.trim().isEmpty() && bearerToken.startsWith("Bearer ")){
            accessToken = bearerToken.substring(7);
            try{
                Claims claims =Jwts.parser()
                        .setSigningKey(secretKey)
                        .parseClaimsJws(accessToken)
                        .getBody();
                return true;
            } catch (ExpiredJwtException | MalformedJwtException e){
                return false;
            }
        }
        return false;
    }


}
