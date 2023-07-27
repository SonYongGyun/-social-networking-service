package kr.co.mz.sns.util;

import kr.co.mz.sns.config.security.CustomUserDetails;
import kr.co.mz.sns.dto.user.GenericUserDto;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class CurrentUserInfo {

  private GenericUserDto currentUserDto() {
    var authentication = SecurityContextHolder.getContext().getAuthentication();
    return ((CustomUserDetails) authentication.getPrincipal()).getUserDto();
  }

  public String getEmail() {
    return currentUserDto().getEmail();
  }

  public Long getSeq() {
    return currentUserDto().getSeq();
  }
}
