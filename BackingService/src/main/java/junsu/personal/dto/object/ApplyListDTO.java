package junsu.personal.dto.object;

import junsu.personal.auth.UserType;
import junsu.personal.entity.MatchEntity;
import lombok.Builder;

import java.util.ArrayList;
import java.util.List;

@Builder
public record ApplyListDTO(
        String userId,
        String status,
        String desc,
        String writeDatetime
) {
    // Java에서는 생성자 체이닝에서 this() 호출이 생성자 첫 번째 문장에 수행되어야 하지만 
    // userType으로 분류를 해야함으로 Factory Method 생성
    public static ApplyListDTO fromMatchEntity(MatchEntity matchEntity, String userType) {
        if (userType.equals(UserType.STUDENT.getValue())) {
            return new ApplyListDTO(
                    matchEntity.getStudentId(),
                    matchEntity.getStatus(),
                    matchEntity.getContent(),
                    matchEntity.getWriteDatetime()
            );
        } else {
            return new ApplyListDTO(
                    matchEntity.getTeacherId(),
                    matchEntity.getStatus(),
                    matchEntity.getContent(),
                    matchEntity.getWriteDatetime()
            );
        }
    }

    public static List<ApplyListDTO> getList(List<MatchEntity> matchEntities, String userType){
        List<ApplyListDTO> list = new ArrayList<>();
        for(MatchEntity entity : matchEntities){
            ApplyListDTO dto =  ApplyListDTO.fromMatchEntity(entity,userType);
            list.add(dto);
        }

        return list;
    }
}
