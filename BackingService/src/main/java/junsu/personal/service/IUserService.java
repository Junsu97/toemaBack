package junsu.personal.service;

import junsu.personal.dto.response.user.GetSignInUserResponseDTO;
import junsu.personal.dto.response.user.GetUserResponseDTO;
import org.springframework.http.ResponseEntity;

public interface IUserService {
    ResponseEntity<? super GetUserResponseDTO> getUser(String userId);
    ResponseEntity<? super GetSignInUserResponseDTO> getSignInUser(String userId);
}
