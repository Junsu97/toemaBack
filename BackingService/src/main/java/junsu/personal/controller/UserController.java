package junsu.personal.controller;

import jakarta.validation.Valid;
import junsu.personal.dto.request.user.*;
import junsu.personal.dto.response.auth.DeleteUserResponseDTO;
import junsu.personal.dto.response.user.*;
import junsu.personal.service.IFileService;
import junsu.personal.service.IUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
@Slf4j
public class UserController {
    private final IUserService userService;
    private final IFileService fileService;

    @GetMapping("/{userId}")
    public ResponseEntity<? super GetUserResponseDTO> getUser(@PathVariable("userId") String userId) {
        log.info("getUser Start!!!!");
        ResponseEntity<? super GetUserResponseDTO> response = userService.getUser(userId);
        return response;
    }

    /**
     * @param userId @AuthenticationPrincipal 어노테이션은 JWTAuthenticationFilter에 기술한
     *               AbstractAuthenticationToken 타입으로 메모리에 올라간 UserNamePasswordAuthentication()를 이용하여
     *               Context에 담은 UserId정보를 가져오는 것.
     * @return
     */
    @GetMapping("")
    private ResponseEntity<? super GetSignInUserResponseDTO> getSignInUser(@AuthenticationPrincipal String userId) {
        try{
            ResponseEntity<? super GetSignInUserResponseDTO> response = userService.getSignInUser(userId);
            return response;
        }catch (Exception e){
            e.printStackTrace();
            return GetSignInUserResponseDTO.databaseError();
        }
    }

    @GetMapping("/subject")
    private ResponseEntity<? super GetTeacherSubjectResponseDTO> getTeacherSubject(@AuthenticationPrincipal String userId) {
        try{
            ResponseEntity<? super GetTeacherSubjectResponseDTO> response = userService.getTeacherSubject(userId);
            return response;
        }catch (Exception e){
            e.printStackTrace();
            return GetTeacherSubjectResponseDTO.databaseError();
        }
    }

    @PatchMapping("/nickname")
    public ResponseEntity<? super PatchNicknameResponseDTO> patchNickname(
            @RequestBody @Valid PatchNicknameRequestDTO requestBody,
            @AuthenticationPrincipal String userId
    ) {
        ResponseEntity<? super PatchNicknameResponseDTO> response = userService.patchNickname(requestBody, userId);
        return response;
    }

    @PatchMapping("/password")
    public ResponseEntity<? super PatchPasswordResponseDTO> patchPassword(
            @RequestBody @Valid PatchPasswordRequestDTO requestBody,
            @AuthenticationPrincipal String userId
    ) {
        ResponseEntity<? super PatchPasswordResponseDTO> response = userService.patchPassword(requestBody, userId);
        return response;
    }

    @PatchMapping("/profile-image")
    public ResponseEntity<? super PatchProfileImageResponseDTO> patchProfileImage(
            @RequestBody @Valid PatchProfileImageRequestDTO requestBody,
            @AuthenticationPrincipal String userId
    ) {
        log.info("profile-image Start!!!");
        ResponseEntity<? super PatchProfileImageResponseDTO> response = userService.patchProfileImage(requestBody, userId);
        return response;
    }

    @PatchMapping("/edit-user")
    public ResponseEntity<? super PatchUserResponseDTO> patchUSer(
            @RequestBody @Valid PatchUserRequestDTO requestBody,
            @AuthenticationPrincipal String userId
    ){
        ResponseEntity<? super PatchUserResponseDTO> response = userService.patchUser(requestBody, userId);
        return response;
    }

    @PostMapping("/send-mail")
    public ResponseEntity<? super PostMailSendResponseDTO> postMailSend(@RequestBody PostMailSendRequestDTO requestBody){
        ResponseEntity<? super PostMailSendResponseDTO> response = userService.postMailSend(requestBody);
        return response;
    }

    @PostMapping("/receive-mail")
    public ResponseEntity<? super PostMailReceiveResponseDTO> postMailReceive(@RequestBody PostMailReceiveRequestDTO requestBody){
        ResponseEntity<? super PostMailReceiveResponseDTO> resposne = userService.postMailReceive(requestBody);
        return resposne;
    }

    @PatchMapping("/update/{userId}")
    public ResponseEntity patchUserInfo(){
        return null;
    }

    @PostMapping("/find-id")
    public ResponseEntity<? super PostFindUserIdResponseDTO> postFindUserId(@RequestBody @Valid PostFindUserIdRequestDTO requestBody){
        log.info("findId Start!!!!!!!!!!!!!!!!!!!");
        ResponseEntity<? super PostFindUserIdResponseDTO> response = userService.postFindUserId(requestBody);
        log.info("findId End!!!!!!!!!!!!!!!!!!!");
        return response;
    }
    @PostMapping("/password")
    public ResponseEntity<? super PostPasswordResponseDTO> postPassword(
            @RequestBody @Valid PostPasswordRequestDTO requestBody
    ) {
        ResponseEntity<? super PostPasswordResponseDTO> response = userService.postPassword(requestBody);
        return response;
    }

    @PostMapping("/check-password")
    public ResponseEntity<? super PostCheckPasswrodResponseDTO> postCheckPassword(
            @RequestBody @Valid PostCheckPasswordRequestDTO requestBody,
            @AuthenticationPrincipal String userId
    ){
        ResponseEntity<? super PostCheckPasswrodResponseDTO> response = userService.postCheckPassword(requestBody, userId);
        return response;
    }

    @DeleteMapping("/delete/{userType}")
    public ResponseEntity<? super DeleteUserResponseDTO> deleteUser(@PathVariable String userType, @AuthenticationPrincipal String userId){
        ResponseEntity<? super DeleteUserResponseDTO> response = userService.deleteUser(userType,userId);
        return response;
    }
}
