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
public class AuthController {
    private final AuthService authService;

    @Value("${univCert.api.key}")
    private String univCertAPI;

    @ApiOperation(value = "아이디 비밀번호를 입력해주세요", notes =  "로그인 API입니다.")
    @PostMapping("/sign-in")
    public ResponseEntity<? super SignInResponseDTO> signIn(@RequestBody @Valid SignInRequestDTO requestBody){
        ResponseEntity<? super SignInResponseDTO> response = authService.signIn(requestBody);
        return response;
    }

    @PostMapping("/sign-up")
    public ResponseEntity<? super SignUpResponseDTO> signUp(@RequestBody @Valid SignUpRequestDTO requestBody){
        ResponseEntity<? super SignUpResponseDTO> response = authService.signUp(requestBody);
        return response;
    }

    //대학메일 인증
    @PostMapping(value = "/validation/teacher/mail/send")
    public JSONObject sendUnivCertMain(@RequestBody @Valid MailDTO mailDTO) throws Exception{
//        Map<String, Object> response = UnivCert.certify(univCertAPI, mailDTO.univName(), mailDTO.email(),true);
        Map<String, Object> response = UnivCert.certify(univCertAPI, mailDTO.univName(), mailDTO.email(), true);
        return new JSONObject(response);
    }

    @PostMapping(value = "/validation/student/mail/send")
    public JSONObject sendMail(@RequestBody @Valid MailDTO mailDTO) throws Exception{
        Map<String, Object> response = UnivCert.certify(univCertAPI, mailDTO.univName(), mailDTO.email(), false);
        return new JSONObject(response);
    }

    @ApiOperation(value = "인증코드 입력", notes = "인증코드 필수, 1000~9999의 인증번호 양식 준수 \n"+
            "success : true면 끝")
    @PostMapping("/validation/mail/receive")
    public ResponseEntity<? super SignUpResponseDTO> receiveUnivCertMail(@RequestBody MailDTO mailDTO) throws Exception {
        Map<String, Object> response = UnivCert.certifyCode(univCertAPI, mailDTO.email(), mailDTO.univName(), mailDTO.code());
        boolean success = (boolean) response.get("success");
        if (success) {
            ResponseEntity<? super SignUpResponseDTO> res = authService.validateUnivEmail(mailDTO);
            return res;
        } else {
            // 인증에 실패한 경우에 대한 처리
            return ResponseEntity.badRequest().body(SignUpResponseDTO.validateUniversityEmailVerification());
        }
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
