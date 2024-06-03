package junsu.personal.service;

import junsu.personal.dto.request.homework.PatchHomeworkRequestDTO;
import junsu.personal.dto.request.homework.PostHomeworkRequestDTO;
import junsu.personal.dto.response.homework.*;
import org.springframework.http.ResponseEntity;

public interface IHomeworkService {
    ResponseEntity<? super GetHomeworkListResponseDTO> getHomeworkFromDate(String studentId, String teacherId, String date);
    ResponseEntity<? super GetHomeworkListResponseDTO> getHomeworkList(String studentId, String teacherId);

    ResponseEntity<? super PostHomeworkResponseDTO> postHomework(PostHomeworkRequestDTO requestBody, String userId);
    ResponseEntity<? super PatchHomeworkResponseDTO> patchHomework(PatchHomeworkRequestDTO requestBody, Long seq, String userId);

    ResponseEntity<? super DeleteHomeworkResponseDTO> deleteHomework(Long seq, String userId);
}
