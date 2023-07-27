package kr.co.mz.sns.service.user;

import kr.co.mz.sns.dto.user.FindUserDetailDto;
import kr.co.mz.sns.dto.user.InsertUserDetailDto;
import kr.co.mz.sns.dto.user.UpdateUserDetailDto;
import kr.co.mz.sns.entity.user.UserDetailEntity;
import kr.co.mz.sns.exception.NotFoundException;
import kr.co.mz.sns.repository.user.UserDetailRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class UserDetailService {

  private final UserDetailRepository userDetailRepository;
  private final ModelMapper modelMapper;
  private final UserService userService;
  private final UserProfileService userProfileService;

  public InsertUserDetailDto findByUserSeq(Long userSeq) {
    return modelMapper.map(userDetailRepository.findById(userSeq).orElseGet(UserDetailEntity::new),
        InsertUserDetailDto.class);
  }

  public FindUserDetailDto findByEmail(String email) {
    var userSeq = userService.findEntityByUserEmail(email).getSeq();

    var findUserDetailDto = modelMapper
        .map(
            userDetailRepository
                .findById(userSeq)
                .orElseThrow(
                    () -> new NotFoundException("등록된 상세 정보가 없습니다. 지금 바로 작성 해 보세 요!")
                ),
            FindUserDetailDto.class);
    findUserDetailDto.setUserDetailFileDtoSet(userProfileService.findAll(userSeq));
    return findUserDetailDto;
  }

  @Transactional
  public InsertUserDetailDto insert(InsertUserDetailDto insertUserDetailDto) {
    var userDetailEntity = modelMapper.map(insertUserDetailDto, UserDetailEntity.class);
    var savedEntity = userDetailRepository.save(
        userDetailEntity);//todo insertfailed exception 이녀석은 바로위의 엔티티랑 같은녀석이다 참조까지 같다. jpa설명에있다.
    return modelMapper.map(savedEntity, InsertUserDetailDto.class);
  }

  @Transactional
  public InsertUserDetailDto updateByUserSeq(UpdateUserDetailDto updateUserDetailDto) {
    var optionalUserDetailEntity = userDetailRepository.findByUserSeq(updateUserDetailDto.getUserSeq());
    var userDetailEntity = optionalUserDetailEntity.orElseThrow(
        () -> new NotFoundException("Oops! No existing detail! Insert your detail first!"));
    userDetailEntity.setGreeting(updateUserDetailDto.getGreeting());
    //todo fileEntity가 필요하네..?
//    var userDetailEntity = modelMapper.map(updateUserDetailDto, UserDetailEntity.class);
    var updatedEntity = userDetailRepository.save(userDetailEntity);
    return modelMapper.map(updatedEntity, InsertUserDetailDto.class);
  }

  //나는 삭제만 할거라고 알고 있다. 근데 다른 사람이 이녀석을 쓸 때, 트랜잭션 걸고할껏.
  //일단 트랜잭션공부가 더 필요.
  @Transactional
  public long deleteByUserSeq(Long userSeq) {
    return userDetailRepository.deleteByUserSeq(userSeq);
  }

  // 완벽하게 dto 랑 맞지 않으니까.왜? default 로 생성되는 db설정들 외 기타 요인들 떄문.
  // 업데이트 후 업데이트 된 entitiy 를 다시 dto로 만들어서 보내준다.


}
