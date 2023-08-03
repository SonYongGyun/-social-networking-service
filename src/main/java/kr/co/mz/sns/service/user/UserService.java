package kr.co.mz.sns.service.user;

import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.Optional;
import kr.co.mz.sns.dto.login.RegisterUserDto;
import kr.co.mz.sns.dto.user.friend.MentionedFriendDto;
import kr.co.mz.sns.entity.user.UserDetailEntity;
import kr.co.mz.sns.entity.user.UserEntity;
import kr.co.mz.sns.enums.Role;
import kr.co.mz.sns.exception.NotFoundException;
import kr.co.mz.sns.exception.ResourceAlreadyExistsException;
import kr.co.mz.sns.repository.user.UserDetailRepository;
import kr.co.mz.sns.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class UserService {

  private final UserRepository userRepository;
  private final UserDetailRepository userDetailRepository;
  private final ModelMapper modelMapper;
  private final PasswordEncoder passwordEncoder;

  public UserEntity findByUserEmail(String email) {
    return userRepository.findByEmail(email)
        .orElseThrow(() -> new NotFoundException(""));
  }

  public UserEntity findBySeq(Long seq) {
    return userRepository.findBySeq(seq)
        .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + seq));
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
    var user = userRepository.findBySeq(seq)
        .orElseThrow(() -> new EntityNotFoundException("User not found with seq: " + seq));
    log.debug(user.toString());
    var userDetail = Optional.ofNullable(user.getUserDetail()).orElseGet(() -> {
      var newUserDetail = UserDetailEntity.builder()
          .detailSeq(seq)
          .userEntity(user)
          .blocked(false)
          .lastLoginAt(now)
          .createdAt(now)
          .build();
      user.setUserDetail(newUserDetail);
      return newUserDetail;
    });

    userDetail.setLastLoginAt(now);
    user.setUserDetail(userDetail);
    userRepository.save(user);

    return now;
  }


  public MentionedFriendDto findDetailByUserName(String userName) {
    return modelMapper
        .map(
            userRepository
                .findByName(userName)
                .orElseThrow(),
            MentionedFriendDto.class
        );
  }

}
