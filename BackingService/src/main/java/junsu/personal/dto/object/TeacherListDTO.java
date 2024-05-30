package junsu.personal.dto.object;

import junsu.personal.entity.TeacherSubjectEntity;
import lombok.Builder;

import java.util.ArrayList;
import java.util.List;

@Builder
public record TeacherListDTO(
        String userId,
        Boolean korean,
        Boolean math,
        Boolean science,
        Boolean social,
        Boolean english,
        String desc,
        String profileImage,
        String school
) {
    public TeacherListDTO(TeacherSubjectEntity teacherSubjectEntity) {
        this(teacherSubjectEntity.getUserId(),
                teacherSubjectEntity.getKorean(),
                teacherSubjectEntity.getMath(),
                teacherSubjectEntity.getScience(),
                teacherSubjectEntity.getSocial(),
                teacherSubjectEntity.getEnglish(),
                teacherSubjectEntity.getDesc(),
                teacherSubjectEntity.getProfileImage(),
                teacherSubjectEntity.getSchool());
    }

    public static List<TeacherListDTO> getList(List<TeacherSubjectEntity> teacherSubjectEntities) {
        List<TeacherListDTO> list = new ArrayList<>();
        for (TeacherSubjectEntity entity : teacherSubjectEntities) {
            TeacherListDTO teacherListDTO = new TeacherListDTO(entity);
            list.add(teacherListDTO);
        }
        return list;
    }
}
