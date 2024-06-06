package junsu.personal.controller;


import jakarta.validation.Valid;
import junsu.personal.dto.request.homework.PatchHomeworkRequestDTO;
import junsu.personal.dto.request.homework.PostHomeworkRequestDTO;
import junsu.personal.dto.response.homework.DeleteHomeworkResponseDTO;
import junsu.personal.dto.response.homework.GetHomeworkListResponseDTO;
import junsu.personal.dto.response.homework.PatchHomeworkResponseDTO;
import junsu.personal.dto.response.homework.PostHomeworkResponseDTO;
import junsu.personal.service.impl.HomeworkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/homework")
@RequiredArgsConstructor
public class HomeworkController {
    private final HomeworkService homeworkService;

    @GetMapping("{teacherUserId}/{studentUserId}/list/{date}")
    public ResponseEntity<? super GetHomeworkListResponseDTO> getHomeworkListFromDate(@PathVariable String teacherUserId,
                                                                                      @PathVariable String studentUserId,
                                                                                      @PathVariable String date){
        ResponseEntity<? super GetHomeworkListResponseDTO> response = homeworkService.getHomeworkFromDate(studentUserId, teacherUserId, date);
        return response;
    }

    @GetMapping("from-teacher/{teacherUserId}/{studentUserId}/list")
    public ResponseEntity<? super GetHomeworkListResponseDTO> getHomeworkList(@PathVariable String teacherUserId,
                                                                              @PathVariable String studentUserId){
        ResponseEntity<? super GetHomeworkListResponseDTO> response = homeworkService.getHomeworkList(studentUserId, teacherUserId);
        return response;
    }
    @GetMapping("from-student/{studentUserId}/list")
    public ResponseEntity<? super GetHomeworkListResponseDTO> getHomeworkList(
                                                                              @PathVariable String studentUserId){
        ResponseEntity<? super GetHomeworkListResponseDTO> response = homeworkService.getHomeworkList(studentUserId);
        return response;
    }

    @PostMapping("write")
    public ResponseEntity<? super PostHomeworkResponseDTO> postHomework(@RequestBody @Valid PostHomeworkRequestDTO requestBody, @AuthenticationPrincipal String userId){
        ResponseEntity<? super PostHomeworkResponseDTO> response = homeworkService.postHomework(requestBody, userId);
        return response;
    }

    @PatchMapping("update/{seq}")
    public ResponseEntity<? super PatchHomeworkResponseDTO> patchHomework(@RequestBody @Valid PatchHomeworkRequestDTO requestBody,
                                                                          @PathVariable Long seq,
                                                                          @AuthenticationPrincipal String userId){
        ResponseEntity<? super PatchHomeworkResponseDTO> response = homeworkService.patchHomework(requestBody, seq, userId);
        return response;
    }

    @DeleteMapping("delete/{seq}")
    public ResponseEntity<? super DeleteHomeworkResponseDTO> deleteHomework(@PathVariable Long seq, @AuthenticationPrincipal String userId){
        ResponseEntity<? super DeleteHomeworkResponseDTO> response = homeworkService.deleteHomework(seq, userId);
        return response;
    }
}
