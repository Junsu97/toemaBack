package junsu.personal.service.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import junsu.personal.auth.UserType;
import junsu.personal.dto.request.teacher.GetApplyListRequestDTO;
import junsu.personal.dto.request.teacher.PatchApplyRequestDTO;
import junsu.personal.dto.request.teacher.PostApplyTeacherRequestDTO;
import junsu.personal.dto.response.ResponseDTO;
import junsu.personal.dto.response.teacher.*;
import junsu.personal.entity.MatchEntity;
import junsu.personal.entity.TeacherSubjectEntity;
import junsu.personal.entity.TeacherUserEntity;
import junsu.personal.repository.MatchRepository;
import junsu.personal.repository.TeacherSubjectRepository;
import junsu.personal.repository.TeacherUserRepository;
import junsu.personal.repository.resultSet.GetTeacherInfoResultSet;
import junsu.personal.service.ITeacherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TeacherService implements ITeacherService {
    private final TeacherSubjectRepository teacherSubjectRepository;
    private final MatchRepository matchRepository;
    private final EntityManager entityManager;

    private final TeacherUserRepository teacherUserRepository;

    @Override
    public ResponseEntity<? super GetTeacherInfoResponseDTO> getTeacher(String userId) {
        GetTeacherInfoResultSet resultSet = null;
        try {
            resultSet = teacherUserRepository.getTeacher(userId);
            if (resultSet == null) return GetTeacherInfoResponseDTO.noExistsUser();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDTO.databaseError();
        }

        return GetTeacherInfoResponseDTO.success(resultSet);
    }

    @Override
    public ResponseEntity<? super GetTeacherListResponseDTO> getTeacherList(String sub1, String sub2, String sub3, String sub4, String sub5) {
        List<TeacherSubjectEntity> teacherSubjectEntities = new ArrayList<>();

        try {
            CriteriaBuilder builder = entityManager.getCriteriaBuilder();
            CriteriaQuery<TeacherSubjectEntity> query = builder.createQuery(TeacherSubjectEntity.class);
            Root<TeacherSubjectEntity> root = query.from(TeacherSubjectEntity.class);
            List<Predicate> predicates = new ArrayList<>();

            if (sub1 != null && !sub1.isEmpty()) {
                predicates.add(builder.equal(root.get(sub1), true));
            }
            if (sub2 != null && !sub2.isEmpty()) {
                predicates.add(builder.equal(root.get(sub2), true));
            }
            if (sub3 != null && !sub3.isEmpty()) {
                predicates.add(builder.equal(root.get(sub3), true));
            }
            if (sub4 != null && !sub4.isEmpty()) {
                predicates.add(builder.equal(root.get(sub4), true));
            }
            if (sub5 != null && !sub5.isEmpty()) {
                predicates.add(builder.equal(root.get(sub5), true));
            }

            if (!predicates.isEmpty()) {
                query.where(builder.or(predicates.toArray(new Predicate[0])));
            }

            TypedQuery<TeacherSubjectEntity> typedQuery = entityManager.createQuery(query);
            teacherSubjectEntities = typedQuery.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDTO.databaseError();
        }

        return GetTeacherListResponseDTO.success(teacherSubjectEntities);

    }

    @Override
    public ResponseEntity<? super GetApplyListResponseDTO> getApplyList(GetApplyListRequestDTO requestBody) {
        List<MatchEntity> matchEntities = new ArrayList<>();
        String userType = requestBody.userType();
        String userId = requestBody.userId();
        try {
            if(userType.equals(UserType.STUDENT.getValue())){
                matchEntities = matchRepository.findByStudentIdOrderByWriteDatetimeDesc(userId);
            }else{
                matchEntities = matchRepository.findByTeacherIdOrderByWriteDatetimeDesc(userId);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDTO.databaseError();
        }
        return GetApplyListResponseDTO.success(matchEntities, userType);
    }

    @Override
    public ResponseEntity<? super GetApplyInfoResponseDTO> getApplyInfo(String teacherId, String studentId) {
        MatchEntity matchEntity = null;
        try {
            matchEntity = matchRepository.findByTeacherIdAndStudentId(teacherId, studentId);
            log.info("teacherId : " + teacherId + " studentId : " + studentId);
            if (matchEntity == null) {
                return GetApplyInfoResponseDTO.notExistApply();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDTO.databaseError();
        }
        return GetApplyInfoResponseDTO.success(matchEntity);
    }

    @Override
    public ResponseEntity<? super PatchApplyResponseDTO> patchApply(PatchApplyRequestDTO requestBody, String userId) {
        log.info("patchApply 의 studentId" + requestBody.studentId());
        try {
            String studentId = requestBody.studentId();
            String teacherId = requestBody.teacherId();
            String userType = requestBody.userType();
            MatchEntity entity = matchRepository.findByTeacherIdAndStudentId(teacherId, studentId);
            if(userType.equals(UserType.STUDENT.getValue())){
                if(!studentId.equals(userId)){
                    return PatchApplyResponseDTO.noPermission();
                }
            }else{
                if(!teacherId.equals(userId)){
                    return PatchApplyResponseDTO.noPermission();
                }
            }

            if (entity == null) {
                return PatchApplyResponseDTO.notExistApply();
            }
            entity = entity.toBuilder().content(requestBody.content()).status(requestBody.status()).build();
            matchRepository.save(entity);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDTO.databaseError();
        }
        return PatchApplyResponseDTO.success();
    }

    @Override
    public ResponseEntity<? super GetApplyBeforeResponseDTO> getApplyBefore(String userId) {
        try {
            MatchEntity matchEntity = matchRepository.findByStudentId(userId);
            if (matchEntity != null) {
                if (matchEntity.getStatus().equals("신청됨")|| matchEntity.getStatus().equals("승인됨"))
                    return GetApplyBeforeResponseDTO.duplicateApply();
            }
        } catch (Exception e) {
            e.printStackTrace();
            ResponseDTO.databaseError();
        }
        return GetApplyBeforeResponseDTO.success();
    }

    @Override
    public ResponseEntity<? super PostApplyTeacherResponseDTO> postApplyTeacher(PostApplyTeacherRequestDTO requestBody, String userId) {
        String teacherId = requestBody.teacherId();
        String content = requestBody.content();
        String studentId = userId;

        try {
            TeacherUserEntity teacherUserEntity = teacherUserRepository.findByUserId(teacherId);
            if (teacherUserEntity == null) {
                return PostApplyTeacherResponseDTO.notExistUser();
            }
//            MatchEntity temp = matchRepository.findByTeacherIdAndStudentId(teacherId,studentId);
//
//            if(temp == null || temp.getStatus().equals("A")){
            MatchEntity entity = MatchEntity.builder()
                    .teacherId(teacherId)
                    .studentId(studentId)
                    .status("신청됨")
                    .content(content)
                    .build();
            matchRepository.save(entity);
//            }else if(!temp.getStatus().equals("A")){
//
//                temp = temp.toBuilder().status("A").build();
//                matchRepository.save(temp);
//        }
    } catch(
    Exception e)

    {
        e.printStackTrace();
        return ResponseDTO.databaseError();
    }
        return PostApplyTeacherResponseDTO.success();
}
}
