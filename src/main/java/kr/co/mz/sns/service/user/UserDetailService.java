package kr.co.mz.sns.service.user;

import java.util.List;
import kr.co.mz.sns.dto.comment.NotificationDto;
import kr.co.mz.sns.dto.user.WriteUserDetailDto;
import kr.co.mz.sns.dto.user.detail.CompleteUserDetailDto;
import kr.co.mz.sns.dto.user.detail.InsertUserDetailDto;
import kr.co.mz.sns.dto.user.detail.UpdateUserDetailDto;
import kr.co.mz.sns.dto.user.detail.UserDetailAndProfileDto;
import kr.co.mz.sns.dto.user.friend.AFriendDto;
import kr.co.mz.sns.entity.comment.CommentNotificationEntity;
import kr.co.mz.sns.entity.user.UserDetailEntity;
import kr.co.mz.sns.exception.NotFoundException;
import kr.co.mz.sns.repository.comment.CommentNotificationRepository;
import kr.co.mz.sns.repository.user.UserDetailRepository;
import kr.co.mz.sns.repository.user.UserRepository;
import kr.co.mz.sns.util.CurrentUserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Repository
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class UserDetailService {

  private final UserDetailRepository userDetailRepository;
  private final UserProfileService userProfileService;
  private final UserService userService;
  private final ModelMapper modelMapper;
  private final CommentNotificationRepository commentNotificationRepository;
  private final CurrentUserInfo currentUserInfo;
  private final UserRepository userRepository;

  public CompleteUserDetailDto findByUserSeq(Long userSeq) {
    return modelMapper
        .map(
            userDetailRepository
                .findById(userSeq)
                .orElseGet(UserDetailEntity::new),
            CompleteUserDetailDto.class
        );
  }

  //todo user detail 에 name 뺴고 이거에 영향받는 메소드들 고치기


  public UserDetailAndProfileDto findByEmail(String email) {
    var userSeq = userService.findByUserEmail(email).getSeq();

    var findUserDetailDto = modelMapper
        .map(
            userDetailRepository
                .findById(userSeq)
                .orElseThrow(
                    () -> new NotFoundException("등록된 상세 정보가 없습니다. 지금 바로 작성 해 보세 요!")
                ),
            UserDetailAndProfileDto.class);
    findUserDetailDto.setUserDetailFileDtoSet(userProfileService.findAll(userSeq));

    return findUserDetailDto;
  }

  @Transactional
  public WriteUserDetailDto insert(InsertUserDetailDto insertUserDetailDto) {

    var userEntity = userService.findBySeq(insertUserDetailDto.getUserSeq());
    var newUserDetail = new UserDetailEntity();
    newUserDetail.setGreeting(insertUserDetailDto.getGreeting());
    userEntity.setUserDetail(newUserDetail);
    newUserDetail.setUserEntity(userEntity);
    return modelMapper
        .map(
            userRepository.save(userEntity),
            WriteUserDetailDto.class);
  }

  @Transactional
  public CompleteUserDetailDto updateByUserSeq(UpdateUserDetailDto updateUserDetailDto) {
    var updatedEntity = userDetailRepository.findByDetailSeq(updateUserDetailDto.getUserSeq())
        .map(entity -> entity.greeting(updateUserDetailDto.getGreeting()))
        .map(userDetailRepository::save)
        .orElseThrow(() -> new NotFoundException("Oops! No existing detail! Insert your detail first!"));
    return modelMapper
        .map(updatedEntity, CompleteUserDetailDto.class);
  }

  @Transactional
  public CompleteUserDetailDto deleteByUserSeq(Long userSeq) {
    var findUser = userRepository.findBySeq(userSeq).orElseThrow();
    var deletedEntity = userDetailRepository.findByUserEntity(findUser);
    userDetailRepository.deleteByDetailSeq(userSeq);
    return modelMapper
        .map(
            deletedEntity, CompleteUserDetailDto.class
        );
  }

  @Transactional
  public List<NotificationDto> mention(List<String> mentionedNames) {
    return mentionedNames.stream()
        .map(
            name -> userService.findDetailByUserName(name).getUserSeq()
        )
        .map(userSeq -> {
          var notiEntity = new CommentNotificationEntity();
          notiEntity.setMentionerSeq(currentUserInfo.getSeq());
          notiEntity.setReadStatus(false);
          notiEntity.setTargetSeq(userSeq);
          return notiEntity;
        })
        .map(commentNotificationRepository::save)
        .toList()
        .stream().map(notiEntity -> modelMapper.map(notiEntity,
            NotificationDto.class))
        .toList();
  }

  public AFriendDto findByFriendName(String friendName) {
//    var user = userDetailRepository.findByName(friendName);
    return null;
  }
}
