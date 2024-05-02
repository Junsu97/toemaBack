package junsu.personal.dto.object;

import lombok.Builder;

@Builder
public record MailDTO(
        String address,
        String title,
        String message
) {
}
