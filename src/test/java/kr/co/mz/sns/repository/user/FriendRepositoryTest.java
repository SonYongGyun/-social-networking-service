package kr.co.mz.sns.repository.user;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
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

//  @Test
//  @Transactional
//  void testGetFriend() {
//
//    var searchedWithNameAndStatus = friendRelationshipRepository
//        .findByUserEntity_SeqAndFriendEntity_NameAndStatus(
//            51L,
//            "newSlave",
//            FR_WE_ARE_FRIEND
//        );
//    searchedWithNameAndStatus.forEach(
//        dto -> log.info(dto.toString())
//    );
//
//  }

  @Test
  @Transactional
  void testFindAllAsPage() {
// 1대1 매핑되어있어서 디테일, 즉 최소한의 로그인이 없으면 친구신청이 불가하고, 검색조차되지않는다.
    var page = friendRelationshipRepository.findAllByUserSeqAsPage(19L, PageRequest.of(1, 3));
    var a = page.getContent()
        .stream()
        .map(
            dto -> {
              log.info(dto.toString());
              return dto;
            }
        ).toList();
//    var list = friendRelationshipRepository.findAllByUserSeqAsPage(19L, PageRequest.of(0, 5));
//    for (int i = 0; i < list.size(); i++) {
//      log.info(list.get(i).toString());
//    }

//    log.info(String.valueOf(a.size()));
  }
}
