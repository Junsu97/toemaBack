package junsu.personal.service.impl;

import junsu.personal.dto.request.homework.PatchHomeworkRequestDTO;
import junsu.personal.dto.request.homework.PostHomeworkRequestDTO;
import junsu.personal.dto.response.ResponseDTO;
import junsu.personal.dto.response.homework.DeleteHomeworkResponseDTO;
import junsu.personal.dto.response.homework.GetHomeworkResponseDTO;
import junsu.personal.dto.response.homework.PatchHomeworkResponseDTO;
import junsu.personal.dto.response.homework.PostHomeworkResponseDTO;
import junsu.personal.entity.HomeworkEntity;
import junsu.personal.entity.MatchEntity;
import junsu.personal.repository.HomeworkRepository;
import junsu.personal.repository.MatchRepository;
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
    private final MatchRepository matchRepository;
    private final StudentUserRepository studentUserRepository;
    private final TeacherUserRepository teacherUserRepository;

    @Override
    public ResponseEntity<? super GetHomeworkResponseDTO> getHomework(String studentId, String teacherId) {
        List<HomeworkEntity> homeworkEntities = new ArrayList<>();
        try{
            boolean existUser = studentUserRepository.existsByUserId(studentId) && teacherUserRepository.existsByUserId(teacherId);
            if(!existUser) return GetHomeworkResponseDTO.noExistUser();

            MatchEntity matchEntity = matchRepository.findByTeacherIdAndStudentId(teacherId, studentId);
            if(matchEntity == null) return GetHomeworkResponseDTO.noExistMatch();

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

            MatchEntity matchEntity = matchRepository.findByTeacherIdAndStudentId(teacherId, studentId);
            if(matchEntity == null) return GetHomeworkResponseDTO.noExistMatch();

            if(!existUser) return PostHomeworkResponseDTO.noExistUser();

            HomeworkEntity entity = new HomeworkEntity(requestBody);
            homeworkRepository.save(entity);

        }catch (Exception e){
            e.printStackTrace();
            return ResponseDTO.databaseError();
        }
        return PostHomeworkResponseDTO.success();
    }

    @Override
    public ResponseEntity<? super PatchHomeworkResponseDTO> patchHomework(PatchHomeworkRequestDTO requestBody, Long seq, String userId) {
        try{
            HomeworkEntity entity = homeworkRepository.findBySeq(seq);
            if(entity == null){
                return PatchHomeworkResponseDTO.noExistHomework();
            }

            if(!entity.getTeacherId().equals(userId)){
                return PatchHomeworkResponseDTO.noPermission();
            }
            entity.patchHomework(requestBody);
            homeworkRepository.save(entity);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseDTO.databaseError();
        }
        return PatchHomeworkResponseDTO.success();
    }

    @Override
    public ResponseEntity<? super DeleteHomeworkResponseDTO> deleteHomework(Long seq, String userId) {
        try{
            boolean existedUser = homeworkRepository.existsByTeacherId(userId);
            if(!existedUser) return DeleteHomeworkResponseDTO.noExistUser();

            HomeworkEntity entity = homeworkRepository.findBySeq(seq);
            if(entity == null) return DeleteHomeworkResponseDTO.noExistHomework();

            String teacherId = entity.getTeacherId();
            boolean isTeacher = teacherId.equals(userId);

            if(!isTeacher) return DeleteHomeworkResponseDTO.noPermission();

            homeworkRepository.delete(entity);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseDTO.databaseError();
        }

        return DeleteHomeworkResponseDTO.success();
    }
}
