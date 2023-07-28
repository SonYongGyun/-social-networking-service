package kr.co.mz.sns.service.user;

import kr.co.mz.sns.dto.user.GenericFriendDto;
import kr.co.mz.sns.dto.user.RequestedFriendDto;
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
  public RequestedFriendDto friendRequest(Long userSeq, RequestedFriendDto requestedFriendDto) {

    var friendEntity = modelMapper.map(requestedFriendDto, FriendRelationshipEntity.class);

    return null;
  }

  public GenericFriendDto findOne(Long friendSep) {
    var friendEntity = friendRepository.findBySeq(friendSep)
        .orElseThrow(() -> new NotFoundException("친구가 아니거나 친구요청을 하지 않았어요."));
    var friendDetail = userDetailService.findByUserSeq(friendEntity.getFriendSeq());


  }
}
