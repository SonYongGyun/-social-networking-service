package kr.co.mz.sns.service.user;

import java.time.LocalDateTime;
import kr.co.mz.sns.dto.login.RegisterDto;
import kr.co.mz.sns.entity.user.UserEntity;
import kr.co.mz.sns.enums.Role;
import kr.co.mz.sns.exception.NotFoundException;
import kr.co.mz.sns.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
@RequiredArgsConstructor
public class UserService {

  // 순수하게 데이터만 쓸꺼면 그냥 바로 repo 들고오면된다.
  //근데 깔끔하게 처리해주는 로직은 service에 이미 구현되어 있다. 로직이 필요하다면 뭐 이거쓰는거지.
  //이건정답이 없는데 cleanarchitecture 쓰라고하는거다.
  private final UserRepository userRepository;
  private final ModelMapper modelMapper;
  private final PasswordEncoder passwordEncoder;


  @Transactional
  public UserEntity findByUserEmail(String email) {
    if (!userRepository.existsByEmail(email)) {
      throw new NotFoundException("Expected 1 result, but none returned.");
    }
    var userEntity = userRepository.findByEmail(email);
    return userEntity.orElseThrow(() -> new NotFoundException(""));
  }

  @Transactional
  public void register(RegisterDto dto) {
    var userEntity = modelMapper.map(dto, UserEntity.class);
    userEntity.setPassword(passwordEncoder.encode(dto.getPassword()));
    userEntity.setRole(Role.ANONYMOUS.toString());
    userRepository.save(userEntity);
  }

  @Transactional
  public LocalDateTime lastLogin(String email) {
    return userRepository
        .findByEmailWithUserDetail(email)
        .orElseThrow(() -> new UsernameNotFoundException("Wrong User!"))
        .getUserDetail().lastLogin();
  }
}
