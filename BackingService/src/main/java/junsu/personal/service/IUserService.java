package junsu.personal.service;

import junsu.personal.dto.response.user.GetSignInUserResponseDTO;
import org.springframework.http.ResponseEntity;

public interface IUserService {
    ResponseEntity<? super GetSignInUserResponseDTO> getSignInUser(String userId);
}
