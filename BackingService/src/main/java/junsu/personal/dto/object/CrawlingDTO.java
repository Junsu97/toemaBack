package junsu.personal.dto.object;

import lombok.Builder;

@Builder
public record CrawlingDTO(
        String subject,
        String img,
        String url,
        String contents,
        String date,
        String writer
) {
}
