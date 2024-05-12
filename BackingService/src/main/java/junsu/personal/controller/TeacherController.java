package junsu.personal.controller;

import junsu.personal.dto.response.teacher.GetTeacherListResponseDTO;
import junsu.personal.service.ITeacherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/api/v1/teacher")
@RequiredArgsConstructor
@Slf4j
public class TeacherController {
    private final ITeacherService teacherService;

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
}
