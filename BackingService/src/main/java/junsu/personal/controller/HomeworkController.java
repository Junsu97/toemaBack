package junsu.personal.controller;


import jakarta.validation.Valid;
import junsu.personal.dto.request.homework.PatchHomeworkRequestDTO;
import junsu.personal.dto.request.homework.PostHomeworkRequestDTO;
import junsu.personal.dto.response.homework.DeleteHomeworkResponseDTO;
import junsu.personal.dto.response.homework.GetHomeworkResponseDTO;
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

    @GetMapping("{teacherUserId}/{studentUserId}")
    public ResponseEntity<? super GetHomeworkResponseDTO> getHomework(@PathVariable String teacherUserId,
                                                                      @PathVariable String studentUserId){
        ResponseEntity<? super GetHomeworkResponseDTO> response = homeworkService.getHomework(studentUserId, teacherUserId);
        return response;
    }

    @PostMapping("write")
    public ResponseEntity<? super PostHomeworkResponseDTO> postHomework(@RequestBody @Valid PostHomeworkRequestDTO requestBody){
        ResponseEntity<? super PostHomeworkResponseDTO> response = homeworkService.postHomework(requestBody);
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
