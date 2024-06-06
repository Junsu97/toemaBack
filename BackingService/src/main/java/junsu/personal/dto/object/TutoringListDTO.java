package junsu.personal.dto.object;

import junsu.personal.entity.TutoringEntity;
import lombok.Builder;

import java.util.ArrayList;
import java.util.List;

@Builder
public record TutoringListDTO(
        Long seq,
        String studentId,
        String teacherId,
        String tutoringDate,
        String tutoringStartTime,
        String tutoringEndTime,
        String tutoringSubject
) {
    public TutoringListDTO(TutoringEntity entity){
        this(entity.getSeq(), entity.getStudentId(), entity.getTeacherId(),
                entity.getTutoringDate(), entity.getTutoringStartTime(), entity.getTutoringEndTime(),entity.getTutoringSubject());
    }

    public static List<TutoringListDTO> getList(List<TutoringEntity> entities){
        List<TutoringListDTO> list = new ArrayList<>();
        for(TutoringEntity entity : entities){
            TutoringListDTO dto = new TutoringListDTO(entity);
            list.add(dto);
        }

        return list;
    }
}
