package kr.co.mz.sns.repository.user;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional
@SpringBootTest
class UserProfileRepositoryTest {

  @Autowired
  private UserProfileRepository userProfileRepository;

  @Test
  void selectUserProfileSeqs() {

    var seqsList = userProfileRepository.findAllUserProfileSeqsByUserEntity_Seq(19L);
    var deleted = userProfileRepository.deleteAllByUserSeq(seqsList);
    for (int i = 0; i < seqsList.size(); i++) {
      log.info(
          seqsList.get(i).toString()
      );

    }
  }
}
