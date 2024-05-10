package junsu.personal.service;

import junsu.personal.dto.response.teacher.GetTeacherListResponseDTO;
import org.springframework.http.ResponseEntity;

public interface ITeacherService {
    ResponseEntity<? super GetTeacherListResponseDTO> getTeacherList(String sub1, String sub2, String sub3, String sub4, String sub5);
}
