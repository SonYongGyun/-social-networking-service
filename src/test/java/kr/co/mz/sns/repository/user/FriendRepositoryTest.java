package kr.co.mz.sns.repository.user;

import static kr.co.mz.sns.entity.user.constant.FriendRelationshipConst.FR_WE_ARE_FRIEND;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@SpringBootTest
class FriendRepositoryTest {

  @Autowired
  private FriendRelationshipRepository friendRelationshipRepository;
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private ModelMapper modelMapper;

  @Test
  @Transactional
  void testGetFriend() {

    var searchedWithNameAndStatus = friendRelationshipRepository.findByFriendEntity_NameAndStatus("newSlave",
        FR_WE_ARE_FRIEND);
    searchedWithNameAndStatus.forEach(
        dto -> log.info(dto.toString())
    );

  }
}
