package kr.co.mz.sns.entity.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDateTime;
import kr.co.mz.sns.repository.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class UserEntityTest {

  @Autowired
  private UserRepository userRepository;

  @Test
  void testInsertUserAndUserDetailGraph() {
    var now = LocalDateTime.now();
    var user = new UserEntity();
    user.email = "a@a.com";
    user.name = "a-name";
    user.password = "a-password";
    user.role = "ANONYMOUS";
    user.createdAt = now;
    user.modifiedAt = now;

    var userDetail = new UserDetailEntity();
    userDetail.greeting = "a-greeting";
    userDetail.setUserEntity(user);
    userDetail.createdAt = now;
    userDetail.modifiedAt = now;

    user.setUserDetail(userDetail);

    var savedEntity = userRepository.save(user);
    assertNotNull(savedEntity);
    assertEquals(user.seq, savedEntity.seq);
    assertNotNull(savedEntity.userDetail);
  }
}
