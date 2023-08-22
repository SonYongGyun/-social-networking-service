package kr.co.mz.sns.util;

import kr.co.mz.sns.config.security.CustomUserDetails;
import kr.co.mz.sns.user.dto.GenericUserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class CurrentUserInfo {
    private Authentication authentication;


    public void setAuth(Authentication authentication) {
        this.authentication = authentication;
    }

    private GenericUserDto currentUserDto() {
        var authInSecurity = SecurityContextHolder.getContext().getAuthentication();
        if (this.authentication == null) {
            log.info("Principal in SecurityContext");
            return ((CustomUserDetails) authInSecurity.getPrincipal()).getUserDto();
        }
        log.info("Principal in Jwt Controller");
        return ((CustomUserDetails) authentication.getPrincipal()).getUserDto();
    }
    // 로그인하고 그 토큰 쓰면 계속 jwt controller에 있는 auth 사용
    // 서버 재시작하면 계속 security context안에 있는 auth 사용. 왜..?

    public String getEmail() {
        return currentUserDto().getEmail();
    }

    public String getName() {
        return currentUserDto().getName();
    }

    public Long getSeq() {
        return currentUserDto().getSeq();
    }

}
