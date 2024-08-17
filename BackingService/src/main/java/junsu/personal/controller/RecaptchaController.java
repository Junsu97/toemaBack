package junsu.personal.controller;

import junsu.personal.service.impl.RecaptchaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/recaptcha")
@RequiredArgsConstructor
@Slf4j
public class RecaptchaController {
    private final RecaptchaService recaptchaService;

    @PostMapping("/verify-recaptcha")public ResponseEntity<String> verifyRecaptcha(@RequestBody Map<String, String> request) {
        String token= request.get("token");

        boolean isValid= recaptchaService.verifyRecaptcha(token);

        if (isValid) {
            return ResponseEntity.ok("Verification successful");
        } else {
            return ResponseEntity.badRequest().body("Verification failed");
        }
    }
}
