package junsu.personal.dto.request.auth.faceId.object;

public record Expressions(
        double neutral,
        double happy,
        double sad,
        double angry,
        double fearful,
        double disgusted,
        double surprised
) {
}
