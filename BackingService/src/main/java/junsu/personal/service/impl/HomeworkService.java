package junsu.personal.service.impl;

import junsu.personal.dto.request.homework.PatchHomeworkRequestDTO;
import junsu.personal.dto.request.homework.PostHomeworkRequestDTO;
import junsu.personal.dto.response.ResponseDTO;
import junsu.personal.dto.response.homework.GetHomeworkResponseDTO;
import junsu.personal.dto.response.homework.PatchHomeworkResponseDTO;
import junsu.personal.dto.response.homework.PostHomeworkResponseDTO;
import junsu.personal.entity.HomeworkEntity;
import junsu.personal.repository.HomeworkRepository;
import junsu.personal.repository.StudentUserRepository;
import junsu.personal.repository.TeacherUserRepository;
import junsu.personal.service.IHomeworkService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class HomeworkService implements IHomeworkService {
    private final HomeworkRepository homeworkRepository;
    private final StudentUserRepository studentUserRepository;
    private final TeacherUserRepository teacherUserRepository;

    @Override
    public ResponseEntity<? super GetHomeworkResponseDTO> getHomework(String studentId, String teacherId) {
        List<HomeworkEntity> homeworkEntities = new ArrayList<>();
        try{
            boolean existUser = studentUserRepository.existsByUserId(studentId) && teacherUserRepository.existsByUserId(teacherId);
            if(!existUser) return GetHomeworkResponseDTO.noExistUser();

            homeworkEntities = homeworkRepository.findByTeacherIdAndStudentId(teacherId, studentId);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseDTO.databaseError();
        }
        return GetHomeworkResponseDTO.success(homeworkEntities);
    }

    @Override
    public ResponseEntity<? super PostHomeworkResponseDTO> postHomework(PostHomeworkRequestDTO requestBody) {
        try{
            String studentId = requestBody.studentId();
            String teacherId = requestBody.teacherId();

            boolean existUser = studentUserRepository.existsByUserId(studentId) && teacherUserRepository.existsByUserId(teacherId);

            if(!existUser) return PostHomeworkResponseDTO.noExistUser();

            HomeworkEntity entity = HomeworkEntity.builder()
                    .studentId(studentId)
                    .teacherId(teacherId)
                    .startDate(requestBody.startDate())
                    .endDate(requestBody.endDate())
                    .content(requestBody.content())
                    .submit("N")
                    .build();

            homeworkRepository.save(entity);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseDTO.databaseError();
        }
        return PostHomeworkResponseDTO.success();
    }

    @Override
    public ResponseEntity<? super PatchHomeworkResponseDTO> patchHomework(PatchHomeworkRequestDTO requestBody) {
        return null;
    }
}
