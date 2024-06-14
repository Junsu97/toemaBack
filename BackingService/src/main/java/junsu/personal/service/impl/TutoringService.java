package junsu.personal.service.impl;

import junsu.personal.dto.request.tutoring.PatchTutoringRequestDTO;
import junsu.personal.dto.request.tutoring.PostTutoringRequestDTO;
import junsu.personal.dto.response.ResponseDTO;
import junsu.personal.dto.response.tutoring.DeleteTutoringResponseDTO;
import junsu.personal.dto.response.tutoring.GetTutoringListResponseDTO;
import junsu.personal.dto.response.tutoring.PatchTutoringResponseDTO;
import junsu.personal.dto.response.tutoring.PostTutoringResponseDTO;
import junsu.personal.entity.MatchEntity;
import junsu.personal.entity.TutoringEntity;
import junsu.personal.repository.MatchRepository;
import junsu.personal.repository.StudentUserRepository;
import junsu.personal.repository.TeacherUserRepository;
import junsu.personal.repository.TutoringRepository;
import junsu.personal.service.ITutoringService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TutoringService implements ITutoringService {
    private final TutoringRepository tutoringRepository;
    private final MatchRepository matchRepository;
    private final StudentUserRepository studentUserRepository;
    private final TeacherUserRepository teacherUserRepository;
    @Override
    public ResponseEntity<? super GetTutoringListResponseDTO> getTutoringList(String studentId) {
        List<TutoringEntity> tutoringEntities = new ArrayList<>();
        try{
            boolean existUser = studentUserRepository.existsByUserId(studentId);
            if(!existUser) return GetTutoringListResponseDTO.noExistUser();

            tutoringEntities = tutoringRepository.findByStudentIdOrderByTutoringDateAscTutoringStartTimeAsc(studentId);

            if(tutoringEntities.isEmpty() || tutoringEntities == null){
                return GetTutoringListResponseDTO.noExistTutoring();
            }
        }catch (Exception e){
            e.printStackTrace();
            return ResponseDTO.databaseError();
        }
        return GetTutoringListResponseDTO.success(tutoringEntities);
    }

    @Override
    public ResponseEntity<? super GetTutoringListResponseDTO> getTutoringList(String studentId, String teacherId) {
        List<TutoringEntity> tutoringEntities = new ArrayList<>();
        try{
            boolean existUser = studentUserRepository.existsByUserId(studentId) || teacherUserRepository.existsByUserId(teacherId);
            if(!existUser) return GetTutoringListResponseDTO.noExistUser();

            MatchEntity matchEntity = matchRepository.findByTeacherIdAndStudentId(teacherId, studentId);
            if(matchEntity == null) return GetTutoringListResponseDTO.noExistMatch();

            tutoringEntities = tutoringRepository.findByTeacherIdAndStudentIdOrderByTutoringDateAscTutoringDateAsc(teacherId, studentId);

            if(tutoringEntities.isEmpty() || tutoringEntities == null){
                return GetTutoringListResponseDTO.noExistTutoring();
            }
        }catch (Exception e){
            e.printStackTrace();
            return ResponseDTO.databaseError();
        }
        return GetTutoringListResponseDTO.success(tutoringEntities);
    }

    @Override
    public ResponseEntity<? super PostTutoringResponseDTO> postTutoring(PostTutoringRequestDTO requestBody, String userId) {
        try{
            String studentId = requestBody.studentId();
            String teacherId = requestBody.teacherId();

            if(!userId.equals(teacherId)){
                return ResponseDTO.validationFailed();
            }
            boolean existUser = studentUserRepository.existsByUserId(studentId) || (teacherUserRepository.existsByUserId(teacherId) && teacherUserRepository.existsByUserId(userId));
            if(!existUser) return PostTutoringResponseDTO.noExistUser();

            MatchEntity matchEntity = matchRepository.findByTeacherIdAndStudentId(teacherId, studentId);
            if(matchEntity == null) return PostTutoringResponseDTO.noExistMatch();

            TutoringEntity tmp = tutoringRepository.findByStudentIdAndTutoringDate(studentId, requestBody.tutoringDate());
            if(tmp != null){
                LocalTime tmpStartDateTime = LocalTime.parse(tmp.getTutoringStartTime());
                LocalTime tmpEndDateTime = LocalTime.parse(tmp.getTutoringEndTime());
                LocalTime requestBodyStartDateTime = LocalTime.parse(requestBody.tutoringStartTime());
                LocalTime requestBodyEndDateTime = LocalTime.parse(requestBody.tutoringEndTime());
                if((tmpStartDateTime.isAfter(requestBodyStartDateTime) && tmpEndDateTime.isBefore(requestBodyStartDateTime)) || (tmpStartDateTime.isAfter(requestBodyEndDateTime) && tmpEndDateTime.isBefore(requestBodyEndDateTime))){
                    return PostTutoringResponseDTO.existTutoring();
                }
            }

            TutoringEntity entity = new TutoringEntity(requestBody);
            tutoringRepository.save(entity);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseDTO.databaseError();
        }
        return PostTutoringResponseDTO.success();
    }

    @Override
    public ResponseEntity<? super PatchTutoringResponseDTO> patchTutoring(PatchTutoringRequestDTO requestBody, Long seq, String userId) {
        try{
            TutoringEntity entity = tutoringRepository.findBySeq(seq);
            if(entity == null) return PatchTutoringResponseDTO.noExistTutoring();

            if(!entity.getTeacherId().equals(userId)) return PatchTutoringResponseDTO.noPermission();

            TutoringEntity tmp = tutoringRepository.findByStudentIdAndTutoringDate(entity.getStudentId(), requestBody.tutoringDate());
            if(tmp != null){
                LocalDateTime tmpStartDateTime = LocalDateTime.parse(tmp.getTutoringStartTime());
                LocalDateTime tmpEndDateTime = LocalDateTime.parse(tmp.getTutoringEndTime());
                LocalDateTime requestBodyStartDateTime = LocalDateTime.parse(requestBody.tutoringStartTime());
//                LocalDateTime requestBodyEndDateTime = LocalDateTime.parse(requestBody.tutoringEndTime());
                if(tmpStartDateTime.isAfter(requestBodyStartDateTime) && tmpEndDateTime.isBefore(requestBodyStartDateTime)){
                    return PostTutoringResponseDTO.existTutoring();
                }
            }

            entity.patchTutoring(requestBody);
            tutoringRepository.save(entity);
        }catch (Exception e){
            e.printStackTrace();
            ResponseDTO.databaseError();
        }
        return PatchTutoringResponseDTO.success();
    }

    @Override
    public ResponseEntity<? super DeleteTutoringResponseDTO> deleteTutoring(Long seq, String userId) {
        try {
            boolean existUser = tutoringRepository.existsByTeacherId(userId);
            if(!existUser) return DeleteTutoringResponseDTO.noExistUser();

            TutoringEntity entity = tutoringRepository.findBySeq(seq);
            if(entity == null) return DeleteTutoringResponseDTO.noExistTutoring();

            String teacherId = entity.getTeacherId();
            boolean isTeacher = teacherId.equals(userId);

            if(!isTeacher) return DeleteTutoringResponseDTO.noPermission();

            tutoringRepository.delete(entity);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseDTO.databaseError();
        }

        return DeleteTutoringResponseDTO.success();
    }
}
