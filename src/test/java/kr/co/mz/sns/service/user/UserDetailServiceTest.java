package kr.co.mz.sns.service.user;

import kr.co.mz.sns.repository.user.UserDetailRepository;
import kr.co.mz.sns.repository.user.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@Slf4j
public class UserDetailServiceTest {

  @Autowired
  private UserDetailRepository userDetailRepository;
  @Autowired
  private UserRepository userRepository;

  @Autowired
  private UserDetailService userDetailService;
//  @Test
//  void detailUpdateAndDeleteTest() {
//    var findUser = userRepository.findBySeq(19L).get();
//    var existDetail = userDetailRepository.findByUserEntity(findUser);
//    userDetailRepository.delete(existDetail.get());
//    var existDetailforReal = userDetailRepository.findByUserEntity(findUser);
//    assertTrue(existDetailforReal.isEmpty());
//
//  }

  @Test
  void findAllByEmail() {
    var userDetail = userDetailService.findByEmail("admin1@mz.co.kr");
    log.info(userDetail.toString());
  }
}
