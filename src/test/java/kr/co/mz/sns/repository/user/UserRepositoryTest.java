package kr.co.mz.sns.repository.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserRepositoryTest {

  @Autowired
  private UserRepository userRepository;

  @Test
  void testGetUserByEmail() {
    var user1 = userRepository.findByEmail("naver@google.com");
    assertTrue(user1.isEmpty());
    var user2Optional = userRepository.findByEmail("naver@naver.com");
    assertTrue(user2Optional.isPresent());
    var user2 = user2Optional.get();
    assertEquals("naver@naver.com", user2.getEmail());

    var user3Optional = userRepository.findByEmail("google@google.com");
    assertTrue(user3Optional.isPresent());
    var user3 = user3Optional.get();
//    assertEquals("admin", user3.getName());
  }
}
