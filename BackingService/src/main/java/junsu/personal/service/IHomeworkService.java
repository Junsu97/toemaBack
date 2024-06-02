package junsu.personal.service;

import junsu.personal.dto.request.homework.PatchHomeworkRequestDTO;
import junsu.personal.dto.request.homework.PostHomeworkRequestDTO;
import junsu.personal.dto.response.homework.DeleteHomeworkResponseDTO;
import junsu.personal.dto.response.homework.GetHomeworkResponseDTO;
import junsu.personal.dto.response.homework.PatchHomeworkResponseDTO;
import junsu.personal.dto.response.homework.PostHomeworkResponseDTO;
import junsu.personal.entity.HomeworkEntity;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IHomeworkService {
    ResponseEntity<? super GetHomeworkResponseDTO> getHomework(String studentId, String teacherId);

    ResponseEntity<? super PostHomeworkResponseDTO> postHomework(PostHomeworkRequestDTO requestBody);
    ResponseEntity<? super PatchHomeworkResponseDTO> patchHomework(PatchHomeworkRequestDTO requestBody, Long seq, String userId);

    ResponseEntity<? super DeleteHomeworkResponseDTO> deleteHomework(Long seq, String userId);
}
