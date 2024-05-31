package junsu.personal.dto.object;

import junsu.personal.entity.HomeworkEntity;
import lombok.Builder;

import java.util.ArrayList;
import java.util.List;

@Builder
public record HomeworkListDTO(
        String studentId,
        String teacherId,
        String startDate,
        String endDate,
        String content,
        String submit
) {

    public HomeworkListDTO(HomeworkEntity entity){
        this(entity.getStudentId(), entity.getTeacherId(),
                entity.getStartDate(), entity.getEndDate(),
                entity.getContent(), entity.getSubmit());

    }

    public static List<HomeworkListDTO> getList(List<HomeworkEntity> entities){
        List<HomeworkListDTO> list = new ArrayList<>();
        for(HomeworkEntity entity : entities){
            HomeworkListDTO dto = new HomeworkListDTO(entity);
            list.add(dto);
        }

        return list;
    }
}
