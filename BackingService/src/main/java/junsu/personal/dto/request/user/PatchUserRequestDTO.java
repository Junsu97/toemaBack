package junsu.personal.dto.request.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record PatchUserRequestDTO(
        @NotBlank
        String addr,
        String addrDetail,
        String school,
        @NotBlank
        String userType,

        Boolean korean,

        Boolean math,

        Boolean science,

        Boolean social,

        Boolean english,
        String desc

) {
}
