package junsu.personal.service;

import junsu.personal.dto.UserInfoDTO;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface IUserInfoService {
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException;

    public int inuserUserInfo(UserInfoDTO pDTO);

    public UserInfoDTO getUserInfo(UserInfoDTO pDTO) throws Exception;
}
