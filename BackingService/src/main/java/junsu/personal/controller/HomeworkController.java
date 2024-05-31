package junsu.personal.controller;


import junsu.personal.dto.response.homework.GetHomeworkResponseDTO;
import junsu.personal.service.impl.HomeworkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
