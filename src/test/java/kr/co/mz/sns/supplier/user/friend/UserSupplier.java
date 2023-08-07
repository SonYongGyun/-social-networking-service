package kr.co.mz.sns.supplier.user.friend;

import static kr.co.mz.sns.enums.Role.MEMBER;

import kr.co.mz.sns.entity.user.UserEntity;
import kr.co.mz.sns.repository.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
class UserSupplier {

  @Autowired
  private UserRepository userRepository;
  @Autowired
  private PasswordEncoder passwordEncoder;

  @Test
  void supply() {
    for (int i = 0; i < 30; i++) {
      var auser = UserEntity
          .builder()
          .email(i + "muda" + i * 93 + "@mz.co.kr")
          .name(i + "Dio" + i * i)
          .password(passwordEncoder.encode("1"))
          .role(MEMBER.toString())
          .build();
      userRepository.save(auser);
    }
  }
}
