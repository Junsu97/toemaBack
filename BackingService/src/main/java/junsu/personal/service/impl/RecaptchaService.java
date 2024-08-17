package junsu.personal.service.impl;

import junsu.personal.service.IFeignRecaptchaService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class RecaptchaService {
    private final IFeignRecaptchaService recaptchaService;

    @Value("${api.recaptcha.secret.key}")
    private String recaptchaSecretKey;

    public boolean verifyRecaptcha(String token) {
        Map<String, Object> response = recaptchaService.verifyRecaptcha(recaptchaSecretKey, token);

        boolean success = (boolean) response.get("success");
        double score = (double) response.get("score");
        return success && score > 0.5;
    }
}
