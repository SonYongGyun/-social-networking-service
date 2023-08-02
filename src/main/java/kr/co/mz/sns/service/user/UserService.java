package kr.co.mz.sns.service.user;

import java.time.LocalDateTime;
import kr.co.mz.sns.dto.login.RegisterUserDto;
import kr.co.mz.sns.entity.user.UserDetailEntity;
import kr.co.mz.sns.entity.user.UserEntity;
import kr.co.mz.sns.enums.Role;
import kr.co.mz.sns.exception.NotFoundException;
import kr.co.mz.sns.exception.ResourceAlreadyExistsException;
import kr.co.mz.sns.repository.user.UserDetailRepository;
import kr.co.mz.sns.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final UserDetailRepository userDetailRepository;
  private final ModelMapper modelMapper;
  private final PasswordEncoder passwordEncoder;

  public UserEntity findByUserEmail(String email) {
    return userRepository.findByEmail(email)
        .orElseThrow(() -> new NotFoundException(""));
  }

  @Transactional
  public long register(RegisterUserDto dto) {
    if (userRepository.existsByEmail(dto.getEmail())) {
      throw new ResourceAlreadyExistsException("Existing Email entered: " + dto.getEmail());
    }

    var userEntity = modelMapper.map(dto, UserEntity.class);
    userEntity.setPassword(passwordEncoder.encode(dto.getPassword()));
    userEntity.setRole(Role.ANONYMOUS.toString());
    userRepository.save(userEntity);

    return userEntity.getSeq();
  }

  @Transactional
  public LocalDateTime updateLastLogin(Long seq) {
    var now = LocalDateTime.now();
    var userEntity = userDetailRepository.findByUserSeq(seq)
        .orElse(
            UserDetailEntity.builder()
                .userSeq(seq)
                .blocked(false)
                .lastLoginAt(now)
                .build()
        );

    userDetailRepository.save(userEntity);

    return now;
  }
//
//  public CompleteUserDetailDto findDetailByUserName(String userName) {
//    return modelMapper
//        .map(
//            userRepository
//                .findByName(userName)
//                .orElseThrow(),
//            CompleteUserDetailDto.class
//        );
//  }

}
