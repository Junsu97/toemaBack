//package junsu.personal.auth;
//
//import junsu.personal.dto.UserInfoDTO;
//import junsu.personal.util.CmmUtil;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import java.util.Collection;
//import java.util.HashSet;
//import java.util.Set;
//
//@Slf4j
//public record AuthInfo(UserInfoDTO userInfoDTO) implements UserDetails {
//
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        Set<GrantedAuthority> pSet = new HashSet<>();
//
//        String roles = CmmUtil.nvl(userInfoDTO.roles());
//
//        log.info("getAuthorities / roles : " + roles);
//        if(roles.length() > 0){ //DB에 권한정보가 저장되어있을 때만
//            for(String role : roles.split(",")){
//                pSet.add(new SimpleGrantedAuthority(role));
//            }
//        }
//        return pSet;
//    }
//
//    // 사용자 password 반환
//    @Override
//    public String getPassword() {
//        return CmmUtil.nvl(userInfoDTO.password());
//    }
//
//    // 사용자 id 반환
//    @Override
//    public String getUsername() {
//        return CmmUtil.nvl(userInfoDTO.userId());
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return true; // true -> 만료되지 않음
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return true; // true -> 잠금되지 않음
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true; // true -> 만료되지 않았음
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return true; // true -> 사용가능
//    }
//}
