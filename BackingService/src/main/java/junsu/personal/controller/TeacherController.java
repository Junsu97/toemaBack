package junsu.personal.controller;

import jakarta.validation.Valid;
import junsu.personal.dto.request.teacher.GetApplyListRequestDTO;
import junsu.personal.dto.request.teacher.PatchApplyRequestDTO;
import junsu.personal.dto.request.teacher.PostApplyTeacherRequestDTO;
import junsu.personal.dto.response.teacher.*;
import junsu.personal.service.ITeacherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value="/api/v1/teacher")
@RequiredArgsConstructor
@Slf4j
public class TeacherController {
    private final ITeacherService teacherService;

    @GetMapping("/{teacherUserId}")
    public ResponseEntity<? super GetTeacherInfoResponseDTO> getTeacher(@PathVariable("teacherUserId") String userId){
        ResponseEntity<? super GetTeacherInfoResponseDTO> response = teacherService.getTeacher(userId);
        return response;
    }

    @GetMapping("/{teacherId}/{studentId}")
    public ResponseEntity<? super GetApplyInfoResponseDTO> getApplyInfo(@PathVariable("teacherId") String teacherId, @PathVariable("studentId") String studentId){
        ResponseEntity<? super GetApplyInfoResponseDTO> response = teacherService.getApplyInfo(teacherId, studentId);
        return response;
    }

    @GetMapping(value = {"/list", "/list/{sub1}", "/list/{sub1}/{sub2}",
            "/list/{sub1}/{sub2}/{sub3}", "/list/{sub1}/{sub2}/{sub3}/{sub4}",
            "/list/{sub1}/{sub2}/{sub3}/{sub4}/{sub5}"})
    public ResponseEntity<? super GetTeacherListResponseDTO> getTeacherList(@PathVariable(value = "sub1", required = false) String sub1,
                                                                            @PathVariable(value = "sub2", required = false) String sub2,
                                                                            @PathVariable(value = "sub3", required = false) String sub3,
                                                                            @PathVariable(value = "sub4", required = false) String sub4,
                                                                            @PathVariable(value = "sub5", required = false) String sub5) {
        log.info("getTeacherList Start!!!!");
        ResponseEntity<? super GetTeacherListResponseDTO> response = teacherService.getTeacherList(sub1, sub2, sub3, sub4, sub5);
        log.info("getTeacherList End!!!!!!!!");
        return response;
    }

    @PostMapping(value = "/apply")
    public ResponseEntity<? super PostApplyTeacherResponseDTO> postApplyTeacher(@RequestBody @Valid PostApplyTeacherRequestDTO requestBody,
                                                                                @AuthenticationPrincipal String userId){
        log.info("postApplyTeacher Start!!!");
        ResponseEntity<? super PostApplyTeacherResponseDTO> response = teacherService.postApplyTeacher(requestBody,userId);
        log.info("postApplyTeacher End!!!");
        return response;
    }

    @PatchMapping(value = "/apply")
    public ResponseEntity<? super PatchApplyResponseDTO> patchApply(@RequestBody @Valid PatchApplyRequestDTO requestBody,
                                                                    @AuthenticationPrincipal String userId){
        ResponseEntity<? super PatchApplyResponseDTO> response = teacherService.patchApply(requestBody, userId);
        return response;
    }

    @GetMapping(value = "/apply")
    public ResponseEntity<? super GetApplyBeforeResponseDTO> getApplyBefore(@AuthenticationPrincipal String userId){
        log.info("getApplyBefore Start!!!!");
        ResponseEntity<? super GetApplyBeforeResponseDTO> response = teacherService.getApplyBefore(userId);
        log.info("getApplyBefore End!!!!");
        return response;
    }

    @PostMapping(value = "/apply-list")
    public ResponseEntity<? super GetApplyListResponseDTO> getApplyList(@RequestBody @Valid GetApplyListRequestDTO requestBody){
        ResponseEntity<? super GetApplyListResponseDTO> response = teacherService.getApplyList(requestBody);
        return response;
    }
}
