package junsu.personal.service.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import junsu.personal.dto.response.ResponseDTO;
import junsu.personal.dto.response.teacher.GetTeacherListResponseDTO;
import junsu.personal.entity.TeacherSubjectEntity;
import junsu.personal.repository.TeacherSubjectRepository;
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
    private final EntityManager entityManager;

    @Override
    public ResponseEntity<? super GetTeacherListResponseDTO> getTeacherList(String sub1, String sub2, String sub3, String sub4, String sub5) {
        List<TeacherSubjectEntity> teacherSubjectEntities = new ArrayList<>();
        List<String> conditions = new ArrayList<>();

        try {
            teacherSubjectEntities = teacherSubjectRepository.findAll();
            String baseQuery = "SELECT t FROM TeacherSubjectEntity t WHERE ";
            if (sub1 != null && !sub1.isEmpty()) conditions.add("t." + sub1 + " = 1");
            if (sub2 != null && !sub2.isEmpty()) conditions.add("t." + sub2 + " = 1");
            if (sub3 != null && !sub3.isEmpty()) conditions.add("t." + sub3 + " = 1");
            if (sub4 != null && !sub4.isEmpty()) conditions.add("t." + sub4 + " = 1");
            if (sub5 != null && !sub5.isEmpty()) conditions.add("t." + sub5 + " = 1");

            if (!conditions.isEmpty()) {
                String finalQuery = baseQuery + String.join(" OR ", conditions);
                TypedQuery<TeacherSubjectEntity> query = entityManager.createQuery(finalQuery, TeacherSubjectEntity.class);
                teacherSubjectEntities = query.getResultList();
            }else{
                teacherSubjectEntities = teacherSubjectRepository.findAll();
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDTO.databaseError();
        }

        return GetTeacherListResponseDTO.success(teacherSubjectEntities);

    }
}
