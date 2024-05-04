package junsu.personal.dto.request.faceId;

import junsu.personal.dto.request.faceId.object.Expressions;
import junsu.personal.dto.request.faceId.object.LandMark;

public record PostFaceIDRequestDTO(
        double accuracy,
        Expressions expressions,
        LandMark landMark
) {
}
