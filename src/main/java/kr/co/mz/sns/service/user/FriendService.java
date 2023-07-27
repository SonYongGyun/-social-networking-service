package kr.co.mz.sns.service.user;

import kr.co.mz.sns.dto.user.GenericFriendDto;
import kr.co.mz.sns.dto.user.RequestedFriendDto;
import kr.co.mz.sns.entity.user.FriendEntity;
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
  private final UserService userService;
@Transactional
  public RequestedFriendDto friendRequest(Long userSeq,RequestedFriendDto requestedFriendDto) {


  var friendEntity = modelMapper.map(requestedFriendDto, FriendEntity.class);

  friendRepository

      return
  }
}
