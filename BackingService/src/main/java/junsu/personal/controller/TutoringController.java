package junsu.personal.controller;

import jakarta.validation.Valid;
import junsu.personal.dto.request.tutoring.PatchTutoringRequestDTO;
import junsu.personal.dto.request.tutoring.PostTutoringRequestDTO;
import junsu.personal.dto.response.tutoring.DeleteTutoringResponseDTO;
import junsu.personal.dto.response.tutoring.GetTutoringListResponseDTO;
import junsu.personal.dto.response.tutoring.PatchTutoringResponseDTO;
import junsu.personal.dto.response.tutoring.PostTutoringResponseDTO;
import junsu.personal.service.ITutoringService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/tutoring")
@RequiredArgsConstructor
public class TutoringController {
    private final ITutoringService tutoringService;

    @GetMapping("from-teacher/{teacherUserId}/{studentUserId}/list")
    public ResponseEntity<? super GetTutoringListResponseDTO> getTutoringList(
            @PathVariable String teacherUserId,
            @PathVariable String studentUserId
    ){
        ResponseEntity<? super GetTutoringListResponseDTO> response = tutoringService.getTutoringList(studentUserId,teacherUserId);
        return response;
    }

    @GetMapping("from-student/{studentUserId}/list")
    public ResponseEntity<? super GetTutoringListResponseDTO> getTutoringList(@PathVariable String studentUserId){
        ResponseEntity<? super GetTutoringListResponseDTO> response = tutoringService.getTutoringList(studentUserId);
        return response;
    }

    @PostMapping("write")
    public ResponseEntity<? super PostTutoringResponseDTO> postTutoring(@RequestBody @Valid PostTutoringRequestDTO requestBody, @AuthenticationPrincipal String userId){
        ResponseEntity<? super PostTutoringResponseDTO> response = tutoringService.postTutoring(requestBody, userId);
        return response;
    }

    @PatchMapping("update/{seq}")
    public ResponseEntity<? super PatchTutoringResponseDTO> patchTutoring(@RequestBody @Valid PatchTutoringRequestDTO requestBody,
                                                                          @PathVariable Long seq,
                                                                          @AuthenticationPrincipal String userId){
        ResponseEntity<? super PatchTutoringResponseDTO> response = tutoringService.patchTutoring(requestBody,seq,userId);
        return response;
    }

    @DeleteMapping("delete/{seq}")
    public ResponseEntity<? super DeleteTutoringResponseDTO> deleteTutoring(@PathVariable Long seq, @AuthenticationPrincipal String userId){
        ResponseEntity<? super DeleteTutoringResponseDTO> response = tutoringService.deleteTutoring(seq, userId);
        return response;
    }
}
