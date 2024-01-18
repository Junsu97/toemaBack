package junsu.personal.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", allowedHeaders = {"POST", "GET"}, allowCredentials = "true")
@RestController
@RequestMapping(value = "/api/signUp")
@RequiredArgsConstructor
public class LoginAPIController {

}
