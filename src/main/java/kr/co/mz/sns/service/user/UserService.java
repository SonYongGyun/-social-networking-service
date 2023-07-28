package kr.co.mz.sns.service.user;

import java.time.LocalDateTime;
import java.util.List;
import kr.co.mz.sns.dto.comment.NotificationDto;
import kr.co.mz.sns.dto.login.RegisterDto;
import kr.co.mz.sns.entity.comment.NotificationEntity;
import kr.co.mz.sns.entity.user.UserEntity;
import kr.co.mz.sns.enums.Role;
import kr.co.mz.sns.exception.NotFoundException;
import kr.co.mz.sns.repository.user.NotificationRepository;
import kr.co.mz.sns.repository.user.UserRepository;
import kr.co.mz.sns.util.CurrentUserInfo;
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
  private final NotificationRepository notificationRepository;
  private final CurrentUserInfo currentUserInfo;

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
    var now = LocalDateTime.now();
    userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Wrong User!"))
        .setLastLoginAt(now);
    return now;
  }

  public List<NotificationDto> getTargetSeqByNames(List<String> mentionedNames) {
    return mentionedNames.stream()
        .map(
            name -> userRepository.findByName(name).orElseThrow(() -> new NotFoundException("친구 아닌데?")).getSeq()
        )
        .map(userSeq -> {
          var notiEntity = new NotificationEntity();
          notiEntity.setMentionerSeq(currentUserInfo.getSeq());
          notiEntity.setReadStatus(false);
          notiEntity.setTargetSeq(userSeq);
          return notiEntity;
        })
        .map(notificationRepository::save)
        .toList()
        .stream().map(notiEntity -> modelMapper.map(notiEntity, NotificationDto.class))
        .toList();
  }

}
