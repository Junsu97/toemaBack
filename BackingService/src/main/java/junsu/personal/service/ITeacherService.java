package junsu.personal.service;

import junsu.personal.dto.request.teacher.GetApplyListRequestDTO;
import junsu.personal.dto.request.teacher.PatchApplyRequestDTO;
import junsu.personal.dto.request.teacher.PostApplyTeacherRequestDTO;
import junsu.personal.dto.response.teacher.*;
import org.springframework.http.ResponseEntity;

public interface ITeacherService {
    ResponseEntity<? super GetStudentListResponseDTO> getStudentList(String userId);
    ResponseEntity<? super GetTeacherInfoResponseDTO> getTeacher(String userId);
    ResponseEntity<? super GetTeacherListResponseDTO> getTeacherList(String sub1, String sub2, String sub3, String sub4, String sub5);
    ResponseEntity<? super GetApplyListResponseDTO> getApplyList(GetApplyListRequestDTO requestBody);
    ResponseEntity<? super GetApplyInfoResponseDTO> getApplyInfo(String teacherId, String studentId);
    ResponseEntity<? super PatchApplyResponseDTO> patchApply(PatchApplyRequestDTO requestBody, String userId);
    ResponseEntity<? super GetApplyBeforeResponseDTO> getApplyBefore(String userId);
    ResponseEntity<? super PostApplyTeacherResponseDTO> postApplyTeacher(PostApplyTeacherRequestDTO requestBody, String userId);

}
