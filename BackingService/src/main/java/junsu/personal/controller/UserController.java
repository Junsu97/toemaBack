package junsu.personal.controller;

import junsu.personal.dto.response.user.GetSignInUserResponseDTO;
import junsu.personal.dto.response.user.GetUserResponseDTO;
import junsu.personal.service.impl.UserService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserService userService;
    @GetMapping("/{userId}")
    public ResponseEntity<? super GetUserResponseDTO> getUser(@PathVariable("userId") String userId){
        ResponseEntity<? super GetUserResponseDTO> response = userService.getUser(userId);
        return response;
    }

    /**
     *
     * @param userId @AuthenticationPrincipal 어노테이션은 JWTAuthenticationFilter에 기술한
     *               AbstractAuthenticationToken 타입으로 메모리에 올라간 UserNamePasswordAuthentication()를 이용하여
     *               Context에 담은 UserId정보를 가져오는 것.
     * @return
     */
    @GetMapping("")
    private ResponseEntity<? super GetSignInUserResponseDTO> getSignInUser(@AuthenticationPrincipal String userId){
        ResponseEntity<? super GetSignInUserResponseDTO> response = userService.getSignInUser(userId);
        return response;
    }
}
