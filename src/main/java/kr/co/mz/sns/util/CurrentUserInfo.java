package kr.co.mz.sns.util;

import kr.co.mz.sns.config.security.CustomUserDetails;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class CurrentUserInfo {

  public String getEmail() {
    var authentication = SecurityContextHolder.getContext().getAuthentication();
    return ((CustomUserDetails) authentication.getPrincipal()).getUserDto().getEmail();
  }
}
