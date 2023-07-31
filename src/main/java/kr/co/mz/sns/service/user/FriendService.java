package kr.co.mz.sns.service.user;

import static kr.co.mz.sns.entity.user.constant.FriendRelationshipConst.FR_WE_ARE_FRIEND;

import java.util.function.Function;
import kr.co.mz.sns.dto.user.friend.AcceptFriendRelationshipDto;
import kr.co.mz.sns.dto.user.friend.FriendDetailDto;
import kr.co.mz.sns.dto.user.friend.InsertFriendRelationshipDto;
import kr.co.mz.sns.entity.user.FriendRelationshipEntity;
import kr.co.mz.sns.exception.NotFoundException;
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

  public FriendDetailDto find(Long friendSep) {
    var friendEntity = friendRelationshipRepository.findBySeq(friendSep)
        .orElseThrow(() -> new NotFoundException("친구가 아니거나 친구요청을 하지 않았어요."));
    if (!friendEntity.getStatus().equals(FR_WE_ARE_FRIEND)) {
      //todo
    }
    userDetailService.findByUserSeq(friendEntity.getFriendSeq());
    return null;
  }

  private <FIRST, SECOND, THIRD> THIRD mapAndActAndMap(
      FIRST source, Class<SECOND> secondType, Function<SECOND, SECOND> function, Class<THIRD> thirdType
  ) {
    SECOND intermediate = modelMapper.map(source, secondType);
    SECOND processed = function.apply(intermediate);
    return modelMapper.map(processed, thirdType);
  }
}
