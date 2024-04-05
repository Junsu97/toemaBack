package junsu.personal.dto.request.auth;


import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.*;
import lombok.Builder;

@Builder
//@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public record SignUpRequestDTO(
        /**
         * @NotBlank 는 문자열에서 null값과 공백("   ") 및 빈 문자열("") 을 허용하지 않음
         */
        @NotBlank
        String userId,
        @NotBlank @Size(min=8, max=20,message = "비밀번호는 8자 이상 20자 이하까지 입력가능합니다.")
        String password,
        @NotBlank
        String userName,
        @NotBlank
        String nickname,
        @NotBlank @Pattern(regexp = "^[0-9]{11,13}",message = "11~13자리의 숫자만 입력 가능합니다.")
        String telNumber,
        @NotBlank @Email
        String email,
        @NotBlank
        String addr,
        String addrDetail,
        String profileImage,
        String faceId,
        @NotNull @AssertTrue
        Boolean agreedPersonal
) {
}
