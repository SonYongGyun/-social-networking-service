package kr.co.mz.sns.security;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;
import kr.co.mz.sns.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {

  private final UserRepository userRepository;

  @Autowired
  public CustomUserDetailService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    var user = userRepository.findByName(username)
        .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
    return new User(user.getName(), user.getPassword(),
        mapRolesToAuthorities(Collections.singleton(user.getRole())));
  }

  private Collection<GrantedAuthority> mapRolesToAuthorities(Set<String> roles) {
    return roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
  }

}
