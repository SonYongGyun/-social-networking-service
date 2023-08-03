package kr.co.mz.sns.service.user;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import kr.co.mz.sns.repository.user.UserDetailRepository;
import kr.co.mz.sns.repository.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class UserDetailServiceTest {

  @Autowired
  private UserDetailRepository userDetailRepository;
  @Autowired
  private UserRepository userRepository;

  @Test
  void detailUpdateAndDeleteTest() {
    var findUser = userRepository.findBySeq(6L).get();
    var existDetail = userDetailRepository.findByUserEntity(findUser);
    assertNotNull(existDetail);

  }
}
