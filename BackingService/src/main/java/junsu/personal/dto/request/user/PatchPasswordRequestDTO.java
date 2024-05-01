package junsu.personal.dto.request.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record PatchPasswordRequestDTO(
        @NotBlank
        String password,
        @NotBlank @Size(min=8, max=20,message = "비밀번호는 8자 이상 20자 이하까지 입력가능합니다.")
        String newPassword,
        @NotBlank
        String userType
) {
}
