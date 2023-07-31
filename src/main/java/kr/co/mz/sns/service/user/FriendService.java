package kr.co.mz.sns.service.user;

import static kr.co.mz.sns.entity.user.constant.FriendRelationshipConst.FR_WE_ARE_FRIEND;

import kr.co.mz.sns.dto.user.friend.FriendDetailDto;
import kr.co.mz.sns.dto.user.friend.InsertFriendRelationshipDto;
import kr.co.mz.sns.entity.user.FriendRelationshipEntity;
import kr.co.mz.sns.exception.NotFoundException;
import kr.co.mz.sns.repository.user.FriendRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class FriendService {

  private final FriendRepository friendRepository;
  private final ModelMapper modelMapper;
  private final UserDetailService userDetailService;

  @Transactional
  public InsertFriendRelationshipDto request(InsertFriendRelationshipDto insertFriendRelationshipDto) {
    var friendEntity = modelMapper.map(insertFriendRelationshipDto, FriendRelationshipEntity.class);
    return modelMapper
        .map(
            friendRepository.save(friendEntity),
            InsertFriendRelationshipDto.class
        );
  }

  public FriendDetailDto find(Long friendSep) {
    var friendEntity = friendRepository.findBySeq(friendSep)
        .orElseThrow(() -> new NotFoundException("친구가 아니거나 친구요청을 하지 않았어요."));
    if (!friendEntity.getStatus().equals(FR_WE_ARE_FRIEND)) {
      //todo
    }
    userDetailService.findByUserSeq(friendEntity.getFriendSeq());
    return null;

  }
}
