package junsu.personal.dto.object;

import junsu.personal.entity.StudentUserEntity;
import lombok.Builder;

import java.util.ArrayList;
import java.util.List;

@Builder
public record StudentListDTO(
        String userId,
        String nickname,
        String school,
        String profileImage
) {
    public StudentListDTO(StudentUserEntity userEntity){
        this(userEntity.getUserId(), userEntity.getNickname(), userEntity.getSchool(), userEntity.getProfileImage());
    }

    public static List<StudentListDTO> getList(List<StudentUserEntity> userEntities){
        List<StudentListDTO> list = new ArrayList<>();
        for(StudentUserEntity entity : userEntities){
            StudentListDTO dto = new StudentListDTO(entity);
            list.add(dto);
        }
        return list;
    }
}
