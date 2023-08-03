package kr.co.mz.sns.service.user;

import java.util.function.Function;
import kr.co.mz.sns.dto.user.friend.AFriendDto;
import kr.co.mz.sns.dto.user.friend.AcceptFriendRelationshipDto;
import kr.co.mz.sns.dto.user.friend.InsertFriendRelationshipDto;
import kr.co.mz.sns.entity.user.FriendRelationshipEntity;
import kr.co.mz.sns.repository.user.FriendRelationshipRepository;
import kr.co.mz.sns.util.CurrentUserInfo;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

//todo friend paging
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class FriendService {

  private final FriendRelationshipRepository friendRelationshipRepository;
  private final ModelMapper modelMapper;
  private final CurrentUserInfo currentUserInfo;
  private final UserService userService;


  public Page<AFriendDto> findAllFriendsAsPage(Long userSeq, Pageable pageable) {
    return friendRelationshipRepository
        .findAllByUserSeqAsStream(
            userSeq,
            pageable
        )
        .map(entity -> modelMapper.map(entity, AFriendDto.class));
  }

  @Transactional
  public InsertFriendRelationshipDto request(InsertFriendRelationshipDto insertFriendRelationshipDto) {
    var userSeq = currentUserInfo.getSeq();
    var friendSeq = insertFriendRelationshipDto.getFriendSeq();
    if (friendRelationshipRepository.findByUserEntityAndFriendEntity(userSeq, friendSeq).isPresent()) {
      throw new IllegalArgumentException(
          "A friend request from user " + userSeq + " to user " + friendSeq + " already exists.");
    }
    return mapAndActAndMap(
        insertFriendRelationshipDto
            .userSeq(currentUserInfo.getSeq()),
        FriendRelationshipEntity.class,
        entity -> friendRelationshipRepository.save(
            entity
                .userEntity(userService.findBySeq(userSeq))
                .friendEntity(userService.findBySeq(friendSeq))
        ),
        InsertFriendRelationshipDto.class
    );
  }

  @Transactional
  public AcceptFriendRelationshipDto accept(AcceptFriendRelationshipDto acceptFriendRelationshipDto) {
    return mapAndActAndMap(
        acceptFriendRelationshipDto,
        FriendRelationshipEntity.class,
        friendRelationshipRepository::save,
        AcceptFriendRelationshipDto.class
    );
  }
//
//  public List<FriendDetailDto> findByFriendName(String friendName) {
//    List<FriendRelationshipEntity> friendEntities = friendRelationshipRepository.findByFriendName(friendName);
//    return friendEntities.stream()
//        .filter(friend -> !friend.getStatus().equals(FR_BLOCKED))
//        .map(FriendRelationshipEntity::getUserDetailEntity)
//        .map(userDetailEntity -> modelMapper.map(userDetailEntity, FriendDetailDto.class))
//        .collect(Collectors.toList());
//  }

  private <FIRST, SECOND, THIRD> THIRD mapAndActAndMap(
      FIRST source, Class<SECOND> secondType, Function<SECOND, SECOND> function, Class<THIRD> thirdType
  ) {
    SECOND intermediate = modelMapper.map(source, secondType);
    SECOND processed = function.apply(intermediate);
    return modelMapper.map(processed, thirdType);
  }
}
