package junsu.personal.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.discovery.provider.Serializer;
import junsu.personal.auth.AuthInfo;
import junsu.personal.dto.UserInfoDTO;
import junsu.personal.repository.UserInfoRepository;
import junsu.personal.repository.entity.UserInfoEntity;
import junsu.personal.service.IUserInfoService;
import junsu.personal.util.CmmUtil;
import junsu.personal.util.DateUtil;
import junsu.personal.util.EncryptUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.awt.color.CMMException;
import java.util.Optional;

@Slf4j
@Serializer
@RequiredArgsConstructor
public class UserInfoService implements IUserInfoService {
    private final UserInfoRepository userInfoRepository;

    /**
     * Spring Security에서 로그인 처리를 위해 실행하는 함수
     * Srping Security의 인증 기능을 사용하기 위해선 반드시 만들어야 하는 함수
     * 
     * Contorller로 부터 호출되지않고, Spring Security가 바로 호출됨
     * 아이디로 검색하고, 검색한 결과를 기반으로 Spring Security가 비밀번호가 같은지 판단
     * 아이디와 패스워드가 일치하지 않으면 자동으로 에러 발생
     * @param userId 사용자 아이디
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        log.info(this.getClass().getName() + ".loadUserByUsername Start!!!!");

        // 로그인 요청한 사용자 아이디 검색
        UserInfoEntity rEntity = userInfoRepository.findByUserId(userId)
                .orElseThrow(() -> new UsernameNotFoundException(userId + "Not Found User"));

        UserInfoDTO rDTO = new ObjectMapper().convertValue(rEntity, UserInfoDTO.class);
        return new AuthInfo(rDTO);
    }

    @Override
    public int inuserUserInfo(UserInfoDTO pDTO) {
        log.info(this.getClass().getName() + ".insertUserInfo Start!!!!");

        int res = 0; // 회원가입 성공 : 1, 아이디 중복으로 인한 가입취소 : 2, 기타 에러 : 0

        String userId = CmmUtil.nvl(pDTO.userId());
        String userName = CmmUtil.nvl(pDTO.userName());
        String password = CmmUtil.nvl(pDTO.password());
        String email = CmmUtil.nvl(pDTO.email());
        String addr1 = CmmUtil.nvl(pDTO.addr1());
        String addr2 = CmmUtil.nvl(pDTO.addr2());
        String roles = CmmUtil.nvl(pDTO.roles());

        log.info("userId : " + userId);
        log.info("userName : " + userName);
        log.info("password : " + password);
        log.info("email : " + email);
        log.info("addr1 : " + addr1);
        log.info("addr2 : " + addr2);
        log.info("roles : " + roles);

        // 회원가입 중복 방지를 위해 DB에서 데이터 조회
        Optional<UserInfoEntity> rEntity = userInfoRepository.findByUserId(userId);

        // 값이 존재한다면 (이미 회원가입 된 아이딘)
        if(rEntity.isPresent()){
            res = 2;
        }else{
            // 회원가입을 위한 Entity 생성ㄴ
            UserInfoEntity pEntity = UserInfoEntity.builder()
                    .userId(userId)
                    .userName(userName)
                    .password(password)
                    .email(email)
                    .addr1(addr1)
                    .addr2(addr2)
                    .roles(roles)
                    .regId(userId).regDt(DateUtil.getDateTime("yyyy-MM-dd hh:mm:ss"))
                    .chgId(userId).chgDt(DateUtil.getDateTime("yyyy-mm-dd hh:mm:ss"))
                    .build();
            
            // 회원정보 DB에 저장
            userInfoRepository.save(pEntity);
            
            // JPA의 save함수는 데이터 값에 따라 등록, 수정을 함
            // 내가 실행한 save함수가 DB 등록이 잘 수행되었는지 100% 확신 불가
            // 회원가입 후 , 혹시 저장안될 수 있기에 조회 수행
            // 회원 가입 중복방지를 우해 DB에서 데이터 조회
            rEntity = userInfoRepository.findByUserId(userId);

            if(rEntity.isPresent()){
                res = 1;
            }
        }

        log.info(this.getClass().getName() + ".insertUserInfo End!!!!");

        return res;
    }

    @Override
    public UserInfoDTO getUserInfo(UserInfoDTO pDTO) throws Exception {
        log.info(this.getClass().getName() + ".getUserInfo Start!!!");

        String user_id = CmmUtil.nvl(pDTO.userId());

        log.info("user_id : " + user_id);

        UserInfoDTO rDTO = null;

        Optional<UserInfoEntity> rEntity = userInfoRepository.findByUserId(user_id);

        if(rEntity.isPresent()){
            rDTO = UserInfoDTO.builder()
                    .userId(CmmUtil.nvl(rEntity.get().getUserId()))
                    .userName(CmmUtil.nvl(rEntity.get().getUserName()))

                    //이메일 주소를 복호화해서 Recod 저장하기
                    .email(EncryptUtil.decAES128CBC(CmmUtil.nvl(rEntity.get().getEmail())))
                    .addr1(CmmUtil.nvl(rEntity.get().getAddr1()))
                    .addr2(CmmUtil.nvl(rEntity.get().getAddr2()))
                    .roles(rEntity.get().getRoles())
                    .build();
        }

        log.info(this.getClass().getName() + ".getUserInfo End!!!");
        return rDTO;
    }
}
