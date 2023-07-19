package kr.co.mz.sns.security;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import kr.co.mz.sns.entity.RoleEntity;
import kr.co.mz.sns.entity.UserEntity;
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

  private UserRepository userRepository;

  @Autowired
  public CustomUserDetailService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    UserEntity user = userRepository.findByName(username)
        .orElseThrow(() -> new UsernameNotFoundException("Username not found"));

    return new User(user.getName(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
  }

  private Collection<GrantedAuthority> mapRolesToAuthorities(List<RoleEntity> roles) {
    return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
  }

}
