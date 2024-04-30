package junsu.personal.dto.request.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import org.aspectj.weaver.ast.Not;

@Builder
public record PostFindUserIdRequestDTO(
        @NotBlank
        String userName,
        @NotBlank
        String email,
        @NotBlank
        String userType
) {

}
