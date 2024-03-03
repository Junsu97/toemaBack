//package junsu.personal.controller;
//
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.responses.ApiResponse;
//import io.swagger.v3.oas.annotations.tags.Tag;
//import jakarta.servlet.http.HttpServletRequest;
//import junsu.personal.auth.UserRole;
//import junsu.personal.dto.MsgDTO;
//import junsu.personal.dto.UserInfoDTO;
//import junsu.personal.service.IUserInfoService;
//import junsu.personal.util.CmmUtil;
//import junsu.personal.util.EncryptUtil;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.Optional;
//
//@CrossOrigin(origins = "*", allowedHeaders = {"POST", "GET"}, allowCredentials = "true")
//@Tag(name = "회원가입 API", description = "회원 가입을 위한 API")
//@Slf4j
//@RequestMapping(value = "/api/reg")
//@RequiredArgsConstructor
//@RestController
//public class UserRegController {
//    private final IUserInfoService userInfoService;
//
//    // Spring Security에서 제공하는 비밀번호 암호화 객체(해시 함수)
//    private final PasswordEncoder bCryptPasswordEncoder;
//
//    @Operation(summary = "회원가입 API", description = "회원가입 API",
//            responses = {
//                @ApiResponse(responseCode = "200", description = "OK"),
//                @ApiResponse(responseCode = "404", description = "Page Not Found!")
//            }
//    )
//    @PostMapping(value = "insertUserInfo")
//    public MsgDTO insertUserInfo(HttpServletRequest request){
//        log.info(this.getClass().getName() + ".insertUserInfo Start!!!!");
//
//        int res = 0;
//        String msg = "";
//        MsgDTO dto;
//
//        UserInfoDTO pDTO;
//
//        try{
//            String userId = CmmUtil.nvl(request.getParameter("user_id")); //아이디
//            String userName = CmmUtil.nvl(request.getParameter("user_name")); //이름
//            String password = CmmUtil.nvl(request.getParameter("password")); //비밀번호
//            String email = CmmUtil.nvl(request.getParameter("email")); //이메일
//            String addr1 = CmmUtil.nvl(request.getParameter("addr1")); //주소
//            String addr2 = CmmUtil.nvl(request.getParameter("addr2")); //상세주소
//
//            log.info("userId : " + userId);
//            log.info("userName : " + userName);
//            log.info("password : " + password);
//            log.info("email : " + email);
//            log.info("addr1 : " + addr1);
//            log.info("addr2 : " + addr2);
//
//            pDTO = UserInfoDTO.builder().userId(userId)
//                    .userName(userName)
//                    .password(bCryptPasswordEncoder.encode(password))
//                    .email(EncryptUtil.encAES128CBC(email))
//                    .addr1(addr1).addr2(addr2).roles(UserRole.USER.getValue()).build();
//
//            res = userInfoService.inuserUserInfo(pDTO);
//            log.info("회원 가입 결과(res) : " + res);
//
//            if(res == 1){
//                msg = "회원가입 되었습니다.";
//            } else if (res == 2) {
//                msg = "이미 가입된 아이디 입니다.";
//            }else{
//                msg = "오류로 인해 회원가입이 실패하였습니다.";
//            }
//        }catch (Exception e){
//            msg = "실패하였습니다. : " + e;
//            res = 2;
//            log.info(e.toString());
//            e.printStackTrace();
//        }finally {
//            dto = MsgDTO.builder().result(res).msg(msg).build();
//            log.info(this.getClass().getName() + ".insertUserInfo End!!!!!");
//        }
//
//        return dto;
//    }
//
//    @PostMapping(value = "/getUserIdExists")
//    public MsgDTO getIdExists(HttpServletRequest request) throws Exception{
//        log.info(this.getClass().getName() + ".getUserIdExists Start!!!!");
//
//        String userId = CmmUtil.nvl(request.getParameter("userId"));
//
//        log.info("userId : " + userId);
//
//        UserInfoDTO pDTO;
//        MsgDTO dto;
//        String msg = "";
//        int res = 0;
//        pDTO = UserInfoDTO.builder().userId(userId).build();
//
//        UserInfoDTO rDTO = Optional.ofNullable(userInfoService.getUserInfo(pDTO)).orElseGet(()->UserInfoDTO.builder().build());
//        if(rDTO.userId() == null){
//            msg = "회원가입 가능한 아이디 입니다.";
//            res = 0;
//        }else{
//            msg = "중복된 아이디 입니다.";
//            res = 2;
//        }
//
//        dto = MsgDTO.builder().msg(msg).result(res).build();
//
//        log.info(this.getClass().getName() + ".getUserIdExists End!!!!");
//        return dto;
//    }
//}
