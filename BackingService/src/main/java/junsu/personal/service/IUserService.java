package junsu.personal.service;

import junsu.personal.dto.request.user.PostMailReceiveRequestDTO;
import junsu.personal.dto.request.user.PostMailSendRequestDTO;
import junsu.personal.dto.request.user.*;
import junsu.personal.dto.response.user.PostMailReceiveResponseDTO;
import junsu.personal.dto.response.user.PostMailSendResponseDTO;
import junsu.personal.dto.response.user.*;
import org.springframework.http.ResponseEntity;

public interface IUserService {
    ResponseEntity<? super GetUserResponseDTO> getUser(String userId);
    ResponseEntity<? super PostFindUserIdResponseDTO> postFindUserId(PostFindUserIdRequestDTO pDTO);
    ResponseEntity<? super PostPasswordResponseDTO> postPassword(PostPasswordRequestDTO pDTO);
    ResponseEntity<? super PostMailSendResponseDTO> postMailSend(PostMailSendRequestDTO pDTO);
    ResponseEntity<? super PostMailReceiveResponseDTO> postMailReceive(PostMailReceiveRequestDTO pDTO);

    ResponseEntity<? super PostCheckPasswrodResponseDTO> postCheckPassword(PostCheckPasswordRequestDTO pDTO, String userId);
    ResponseEntity<? super GetSignInUserResponseDTO> getSignInUser(String userId) throws Exception;
    ResponseEntity<? super PatchNicknameResponseDTO> patchNickname(PatchNicknameRequestDTO pDTO, String userId);
    ResponseEntity<? super PatchPasswordResponseDTO> patchPassword(PatchPasswordRequestDTO pDTO, String userId);
    ResponseEntity<? super PatchProfileImageResponseDTO> patchProfileImage(PatchProfileImageRequestDTO pDTO, String userId);
}
