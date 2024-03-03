//package junsu.personal.controller;
//
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.responses.ApiResponse;
//import jakarta.servlet.http.HttpServletResponse;
//import junsu.personal.auth.AuthInfo;
//import junsu.personal.auth.JwtTokenProvider;
//import junsu.personal.auth.JwtTokenType;
//import junsu.personal.dto.MsgDTO;
//import junsu.personal.dto.UserInfoDTO;
//import junsu.personal.util.CmmUtil;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.ResponseCookie;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.Optional;
//
//@CrossOrigin(origins = "*", allowedHeaders = {"POST", "GET"}, allowCredentials = "true")
//@RestController
//@RequestMapping(value = "/api/signUp")
//@RequiredArgsConstructor
//@Slf4j
//public class LoginAPIController {
//    @Value("${jwt.token.access.valid.time}")
//    private long accessTokenValidTime;
//    @Value("${jwt.token.access.name}")
//    private String accessTokenName;
//    @Value("${jwt.token.access.refresh.valid.time}")
//    private long refreshTokenValidTime;
//    @Value("${jwt.token.refresh.name}")
//    private String refreshTokenName;
//
//    private final JwtTokenProvider jwtTokenProvider;
//
//    @Operation(summary = "로그인 성공처리  API", description = "로그인 성공처리 API",
//            responses = {
//                    @ApiResponse(responseCode = "200", description = "OK"),
//                    @ApiResponse(responseCode = "404", description = "Page Not Found!"),
//            }
//    )
//    @PostMapping(value = "loginSucces")
//    public MsgDTO loginSuccess(@AuthenticationPrincipal AuthInfo authInfo,
//                               HttpServletResponse response) {
//        log.info(this.getClass().getName() + ".loginSuccess Start!!!!");
//
//        UserInfoDTO rDTO = Optional.ofNullable(authInfo.userInfoDTO())
//                .orElseGet(() -> UserInfoDTO.builder().build());
//
//        String userId = CmmUtil.nvl(rDTO.userId());
//        String userName = CmmUtil.nvl(rDTO.userName());
//        String userRoles = CmmUtil.nvl(rDTO.roles());
//
//
//        log.info("userId : " + userId);
//        log.info("userName : " + userName);
//        log.info("userRoles : " + userRoles);
//
//        // Access Token 생성
//        String accessToken = jwtTokenProvider.createToken(userId, userRoles, JwtTokenType.ACCESS_TOKEN);
//        log.info("accessToken : " + accessToken);
//
//        ResponseCookie cookie = ResponseCookie.from(accessTokenName, accessToken)
//                .domain("localhost")
//                .path("/")
//                .secure(true)
//                .sameSite("None")
//                .maxAge(accessTokenValidTime)
//                .httpOnly(true)
//                .build();
//
//        // 기존 쿠기 모두 삭제하고 Cokie에 Access 토큰 저장
//        response.setHeader("Set-Cookie", cookie.toString());
//
//
//        cookie = null;
//
//        // Refresh Token 생성
//        // Refresh Token은 보안상 노출되면 위험하기 때문에 Refresh Tokne은 DB에 저장하고
//        // DB를 조회하기 위한 값만 Refresh Token으로 생성
//        // Refresh Token은 Acess Token에 비해 만료시간을 길게 설정한다.
//        String refreshToken = jwtTokenProvider.createToken(userId, userRoles, JwtTokenType.REFRESH_TOKEN);
//        log.info("refreshToken : " + refreshToken);
//
//        cookie = ResponseCookie.from(refreshTokenName, refreshToken)
//                .domain("localhost") // 배포 후 변경
//                .path("/")
//                .secure(true)
//                .sameSite("None")
//                .maxAge(refreshTokenValidTime) // JWT Refresh Token 만료시간 설정
//                .httpOnly(true)
//                .build();
//
//        // 기존 쿠키에 Refresh Token 저장하기
//        // 여기 레디스에 저장하는 코드로 바꿔야함
//        response.addHeader("Set-Cookie", cookie.toString());
//
//        // 결과 메시지 전달하기
//        MsgDTO dto = MsgDTO.builder().result(1).msg(userName + "님 로그인이 되었습니다.").build();
//
//        log.info(this.getClass().getName() + ".loginSuccess End!!!!");
//
//        return dto;
//    }
//
//    @Operation(summary = "로그인 실패처리  API", description = "로그인 실패처리 API",
//            responses = {
//                    @ApiResponse(responseCode = "200", description = "OK"),
//                    @ApiResponse(responseCode = "404", description = "Page Not Found!"),
//            }
//    )
//    @PostMapping(value = "loginFail")
//    public MsgDTO loginFail(){
//        log.info(this.getClass().getName() + ".loginFail Start!!");
//
//        // 결과 메시지 전달하기
//        MsgDTO dto = MsgDTO.builder().result(1).msg("로그인이 실패하였습니다.").build();
//
//        log.info(this.getClass().getName() + ".logfinFail End!!!");
//
//        return dto;
//    }
//}
