package kr.co.mz.sns.service.user;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import kr.co.mz.sns.dto.user.friend.AcceptFriendRelationshipDto;
import kr.co.mz.sns.dto.user.friend.FriendDetailDto;
import kr.co.mz.sns.dto.user.friend.InsertFriendRelationshipDto;
import kr.co.mz.sns.entity.user.FriendRelationshipEntity;
import kr.co.mz.sns.repository.user.FriendRelationshipRepository;
import kr.co.mz.sns.util.CurrentUserInfo;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class FriendService {

  private final FriendRelationshipRepository friendRelationshipRepository;
  private final ModelMapper modelMapper;
  private final UserDetailService userDetailService;
  private final CurrentUserInfo currentUserInfo;

  @Transactional
  public InsertFriendRelationshipDto request(InsertFriendRelationshipDto insertFriendRelationshipDto) {
    return mapAndActAndMap(
        insertFriendRelationshipDto,
        FriendRelationshipEntity.class,
        entity -> friendRelationshipRepository.save(entity.requestedBy(currentUserInfo.getSeq())),
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

  public List<FriendDetailDto> findByFriendName(String friendName) {
    List<FriendRelationshipEntity> friendEntities = friendRelationshipRepository.findByFriendName(friendName);
    return friendEntities.stream()
        .map(FriendRelationshipEntity::getUserDetailEntity)
        .map(userDetailEntity -> modelMapper.map(userDetailEntity, FriendDetailDto.class))
        .collect(Collectors.toList());
  }

  private <FIRST, SECOND, THIRD> THIRD mapAndActAndMap(
      FIRST source, Class<SECOND> secondType, Function<SECOND, SECOND> function, Class<THIRD> thirdType
  ) {
    SECOND intermediate = modelMapper.map(source, secondType);
    SECOND processed = function.apply(intermediate);
    return modelMapper.map(processed, thirdType);
  }
}
