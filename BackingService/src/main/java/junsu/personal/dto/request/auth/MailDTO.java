package junsu.personal.dto.request.auth;

import lombok.Builder;

@Builder
public record MailDTO(
        String userId,
        String email,
        int code,
        String univName,
        boolean check
) {
}
