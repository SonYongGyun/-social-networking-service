package kr.co.mz.sns.util;

import kr.co.mz.sns.config.security.CustomUserDetails;
import kr.co.mz.sns.dto.user.GenericUserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CurrentUserInfo {
    private Authentication authentication;

    public void setAuth(Authentication authentication) {
        this.authentication = authentication;
    }

    private GenericUserDto currentUserDto() {
        return ((CustomUserDetails) authentication.getPrincipal()).getUserDto();


    }

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
