package kr.co.mz.sns.service.user;

import static kr.co.mz.sns.entity.user.constant.FriendRelationshipConst.FR_WAITING_PERMIT_REQUEST;
import static kr.co.mz.sns.entity.user.constant.FriendRelationshipConst.FR_WE_ARE_FRIEND;

import java.util.List;
import java.util.function.Function;
import kr.co.mz.sns.dto.user.friend.AFriendDto;
import kr.co.mz.sns.dto.user.friend.FriendDetailDto;
import kr.co.mz.sns.dto.user.friend.InsertFriendRelationshipDto;
import kr.co.mz.sns.dto.user.friend.RequestedRelationshipDto;
import kr.co.mz.sns.dto.user.friend.ResponseRelationshipDto;
import kr.co.mz.sns.entity.user.FriendRelationshipEntity;
import kr.co.mz.sns.repository.user.FriendRelationshipRepository;
import kr.co.mz.sns.repository.user.UserDetailRepository;
import kr.co.mz.sns.repository.user.UserRepository;
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
  private final UserRepository userRepository;
  private final UserDetailRepository userDetailRepository;


  public Page<AFriendDto> findAllFriendsAsPage(Pageable pageable) {
    return friendRelationshipRepository
        .findAllByUserSeqAsPage(
            currentUserInfo.getSeq(),
            pageable
        )
        .map(entity -> modelMapper.map(entity, AFriendDto.class));
  }

  @Transactional
  public InsertFriendRelationshipDto request(InsertFriendRelationshipDto insertFriendRelationshipDto) {
    var userSeq = currentUserInfo.getSeq();
    var friendSeq = insertFriendRelationshipDto.getFriendSeq();
    if (friendRelationshipRepository.findByUserEntity_SeqAndFriendEntity_Seq(userSeq, friendSeq).isPresent()) {
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
                .status(FR_WAITING_PERMIT_REQUEST)
        ),
        InsertFriendRelationshipDto.class
    );
  }

  @Transactional
  public ResponseRelationshipDto putRelationship(RequestedRelationshipDto requestedRelationshipDto) {
    var responder = userService.findBySeq(currentUserInfo.getSeq());
    var requester = userService.findBySeq(requestedRelationshipDto.getRequesterSeq());

    friendRelationshipRepository
        .findByUserEntity_SeqAndFriendEntity_Seq(
            responder.getSeq(),
            requester.getSeq()
        )
        .ifPresent(
            friendRelationshipEntity -> friendRelationshipEntity.status(requestedRelationshipDto.getStatus())
        );

    return modelMapper.map(
        friendRelationshipRepository
            .findByUserEntity_SeqAndFriendEntity_Seq(
                requester.getSeq(),
                responder.getSeq()
            )
            .orElseThrow(() -> new IllegalArgumentException("상대방이 탈퇴한 회원인 것 같아요."))
            .status(requestedRelationshipDto.getStatus())
        , ResponseRelationshipDto.class
    );
  }

//  @Transactional
//  public List<>

  @Transactional
  public List<FriendDetailDto> findByFriendName(String friendName) {
    return friendRelationshipRepository.findByUserEntity_SeqAndFriendEntity_NameAndStatus(
        currentUserInfo.getSeq(), friendName, FR_WE_ARE_FRIEND);
  }


  private <FIRST, SECOND, THIRD> THIRD mapAndActAndMap(
      FIRST source, Class<SECOND> secondType, Function<SECOND, SECOND> function, Class<THIRD> thirdType
  ) {
    SECOND intermediate = modelMapper.map(source, secondType);
    SECOND processed = function.apply(intermediate);
    return modelMapper.map(processed, thirdType);
  }
}
