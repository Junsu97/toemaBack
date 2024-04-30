package junsu.personal.controller;

import jakarta.validation.Valid;
import junsu.personal.dto.request.user.PatchAndPostPasswordRequestDTO;
import junsu.personal.dto.request.user.PatchNicknameRequestDTO;
import junsu.personal.dto.request.user.PatchProfileImageRequestDTO;
import junsu.personal.dto.request.user.PostFindUserIdRequestDTO;
import junsu.personal.dto.response.user.*;
import junsu.personal.service.IFileService;
import junsu.personal.service.IUserService;
import junsu.personal.service.impl.UserService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
        ResponseEntity<? super GetSignInUserResponseDTO> response = userService.getSignInUser(userId);
        return response;
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
            @RequestBody @Valid PatchAndPostPasswordRequestDTO requestBody,
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
    public ResponseEntity<? super PostPasswordCheckResponseDTO> postPassword(
            @RequestBody @Valid PatchAndPostPasswordRequestDTO requestBody,
            @AuthenticationPrincipal String userId
    ) {
        ResponseEntity<? super PostPasswordCheckResponseDTO> response = userService.postPasswordCheck(requestBody, userId);
        return response;
    }
}
