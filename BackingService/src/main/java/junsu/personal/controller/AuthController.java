package junsu.personal.controller;

import com.fasterxml.jackson.core.JsonParser;
import com.univcert.api.UnivCert;
import io.swagger.annotations.ApiOperation;
import jakarta.validation.Valid;
import junsu.personal.dto.request.auth.*;
import junsu.personal.dto.response.auth.SignInResponseDTO;
import junsu.personal.dto.response.auth.SignUpResponseDTO;
import junsu.personal.service.impl.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    private final AuthService authService;

    @Value("${univCert.api.key}")
    private String univCertAPI;


    @PostMapping("/sign-up")
    public ResponseEntity<? super SignUpResponseDTO> signUp(@RequestBody @Valid SignUpRequestDTO requestBody){
        log.info(this.getClass().getName() + "signUp Start!!!!!!!!");
        ResponseEntity<? super SignUpResponseDTO> response = authService.signUp(requestBody);
        log.info(this.getClass().getName() + "signUp End!!!!!!!!");
        return response;
    }
    
    // 학생 회원가입
//    @PostMapping(value = "/student/sign-up")
//    public ResponseEntity<? super SignUpResponseDTO> studentSignUp(@RequestBody @Valid StudentSignUpRequestDTO requestBody){
//        log.info(this.getClass().getName() + "start studentSignUp!!!!!!!!!!");
//        ResponseEntity<? super SignUpResponseDTO> response = authService.studentSignUp(requestBody);
//        log.info(this.getClass().getName() + "end studentSignUp!!!!!!!!!!");
//        return response;
//    }
//    // 선생 회원가입
//    @PostMapping(value = "/teacher/sign-up")
//    public ResponseEntity<? super SignUpResponseDTO> teacherSignUp(@RequestBody @Valid TeacherSignUpRequestDTO requestBody){
//        log.info(this.getClass().getName() + "start teacherSignUp!!!!!!!!!!");
//        ResponseEntity<? super SignUpResponseDTO> response = authService.teacherSignUp(requestBody);
//        log.info(this.getClass().getName() + "end teacherSignUp!!!!!!!!!!");
//        return response;
//    }

    //대학메일 인증
    @PostMapping(value = "/validation/teacher/mail/send")
    public JSONObject sendUnivCertMain(@RequestBody @Valid MailDTO mailDTO) throws Exception{
        log.info(this.getClass().getName() + "start Teacher sendMail!!!!!!!!!!");
//        Map<String, Object> response = UnivCert.certify(univCertAPI, mailDTO.univName(), mailDTO.email(),true);
        Map<String, Object> response = UnivCert.certify(univCertAPI, mailDTO.univName(), mailDTO.email(), true);
        log.info(response.get("success").toString());
        if(response.get("success").toString().equals("true")){
            log.info("트루!");
        }else{
            log.info("거짓!");
        }
        log.info(this.getClass().getName() + "end sendMail!!!!!!!!!!");
        return new JSONObject(response);
    }

    @PostMapping(value = "/validation/student/mail/send")
    public JSONObject sendMail(@RequestBody @Valid MailDTO mailDTO) throws Exception{
        log.info(this.getClass().getName() + "start Student sendMail!!!!!!!!!!");
        Map<String, Object> response = UnivCert.certify(univCertAPI, mailDTO.univName(), mailDTO.email(), false);
        log.info(this.getClass().getName() + "end  Student sendMail!!!!!!!!!!");
        return new JSONObject(response);
    }

    @ApiOperation(value = "인증코드 입력", notes = "인증코드 필수, 1000~9999의 인증번호 양식 준수 \n"+
            "success : true면 끝")
    @PostMapping("/validation/mail/receive")
    public ResponseEntity<? super SignUpResponseDTO> receiveUnivCertMail(@RequestBody MailDTO mailDTO) throws Exception {
        log.info(this.getClass().getName() + "start receiveUnivCertMail!!!!!!!!!!");
        Map<String, Object> response = UnivCert.certifyCode(univCertAPI, mailDTO.email(), mailDTO.univName(), mailDTO.code());
        boolean success = (boolean) response.get("success");
        if (success) {
            log.info(this.getClass().getName() + "end receiveUnivCertMail!!!!!!!!!!");
            ResponseEntity<? super SignUpResponseDTO> res = authService.validateUnivEmail(mailDTO);
            return res;
        } else {
            log.info(this.getClass().getName() + "end receiveUnivCertMail!!!!!!!!!!");
            // 인증에 실패한 경우에 대한 처리
            return ResponseEntity.badRequest().body(SignUpResponseDTO.validateUniversityEmailVerification());
        }
    }

    @ApiOperation(value = "아이디 비밀번호를 입력해주세요", notes =  "로그인 API입니다.")
    @PostMapping("/sign-in")
    public ResponseEntity<? super SignInResponseDTO> signIn(@RequestBody @Valid SignInRequestDTO requestBody){
        ResponseEntity<? super SignInResponseDTO> response = authService.signIn(requestBody);
        return response;
    }
    /**
     * 인증내역 초기화
     * @throws Exception
     */
    @PostMapping("/validation/mail/clear")
    public void clearUnivCertMail() throws Exception{
        UnivCert.clear(univCertAPI);
    }

    @PostMapping("/certifiedlist")
    public void checkList() throws Exception{
        UnivCert.list(univCertAPI);
    }

    @PostMapping("/checkUnivName")
    private void checkUnivName() throws Exception{
        UnivCert.check("한국폴리텍대학");
    }
}
