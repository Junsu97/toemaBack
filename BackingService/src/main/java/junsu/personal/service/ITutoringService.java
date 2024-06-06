package junsu.personal.service;

import junsu.personal.dto.request.tutoring.PatchTutoringRequestDTO;
import junsu.personal.dto.request.tutoring.PostTutoringRequestDTO;
import junsu.personal.dto.response.tutoring.DeleteTutoringResponseDTO;
import junsu.personal.dto.response.tutoring.GetTutoringListResponseDTO;
import junsu.personal.dto.response.tutoring.PatchTutoringResponseDTO;
import junsu.personal.dto.response.tutoring.PostTutoringResponseDTO;
import org.springframework.http.ResponseEntity;

public interface ITutoringService {
    ResponseEntity<? super GetTutoringListResponseDTO> getTutoringList(String studentId);
    ResponseEntity<? super GetTutoringListResponseDTO> getTutoringList(String studentId, String teacherId);
    ResponseEntity<? super PostTutoringResponseDTO> postTutoring(PostTutoringRequestDTO requestBody, String userId);
    ResponseEntity<? super PatchTutoringResponseDTO> patchTutoring(PatchTutoringRequestDTO requestBody, Long seq, String userId);
    ResponseEntity<? super DeleteTutoringResponseDTO> deleteTutoring(Long seq, String userId);
}
