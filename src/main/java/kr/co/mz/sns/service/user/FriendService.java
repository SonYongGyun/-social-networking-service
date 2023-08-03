package kr.co.mz.sns.service.user;

import java.util.function.Function;
import kr.co.mz.sns.dto.user.friend.AcceptFriendRelationshipDto;
import kr.co.mz.sns.dto.user.friend.InsertFriendRelationshipDto;
import kr.co.mz.sns.entity.user.FriendRelationshipEntity;
import kr.co.mz.sns.repository.user.FriendRelationshipRepository;
import kr.co.mz.sns.util.CurrentUserInfo;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

//todo friend paging, detail u,d
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class FriendService {

  private final FriendRelationshipRepository friendRelationshipRepository;
  private final ModelMapper modelMapper;
  private final CurrentUserInfo currentUserInfo;
  private final UserService userService;

  @Transactional
  public InsertFriendRelationshipDto request(InsertFriendRelationshipDto insertFriendRelationshipDto) {
    return mapAndActAndMap(
        insertFriendRelationshipDto,
        FriendRelationshipEntity.class,
        entity -> friendRelationshipRepository.save(
            entity.userEntity(userService.findBySeq(currentUserInfo.getSeq()))
        ),
        InsertFriendRelationshipDto.class
    );
  }

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
