package kr.co.mz.sns.config.security;

import java.util.Collection;
import kr.co.mz.sns.dto.user.UserDto;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class CustomUserDetails extends User {

  private final UserDto userDto;

  public CustomUserDetails(UserDto userDto, Collection<? extends GrantedAuthority> authorities) {
    super(userDto.getEmail(), userDto.getPassword(), authorities);
    this.userDto = userDto;
  }

  public UserDto getUserDto() {
    System.out.println("userDto : " + userDto);
    return userDto;
  }
}
