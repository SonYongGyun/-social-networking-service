package kr.co.mz.sns.service;

import kr.co.mz.sns.dto.user.UserDetailDto;
import kr.co.mz.sns.dto.user.UserDetailUpdateDto;
import kr.co.mz.sns.entity.UserDetailEntity;
import kr.co.mz.sns.repository.UserDetailRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(propagation = Propagation.SUPPORTS)
public class UserDetailService {

  private final UserDetailRepository userDetailRepository;
  private final ModelMapper modelMapper;

  private final UserService userService;

  @Autowired
  public UserDetailService(UserDetailRepository userDetailRepository, ModelMapper modelMapper,
      UserService userService) {
    this.userDetailRepository = userDetailRepository;
    this.modelMapper = modelMapper;
    this.userService = userService;
  }

  public UserDetailDto findByUserSeq(Long userSeq) {

    return modelMapper.map(userDetailRepository.findById(userSeq).orElseGet(UserDetailEntity::new),
        UserDetailDto.class);
  }

  @Transactional(readOnly = true)
  public UserDetailDto findByEmail(String email) {
    var userSeq = userService.findEntityByUserEmail(email).getSeq();
    return modelMapper.map(userDetailRepository.findById(userSeq).orElseGet(UserDetailEntity::new),
        UserDetailDto.class);
  }


  public UserDetailDto saveOne(UserDetailDto userDetailDto) {
    var userSeq = userDetailDto.getUserSeq();

    var userDetailEntity = modelMapper.map(userDetailDto, UserDetailEntity.class);
    var savedEntity = userDetailRepository.save(
        userDetailEntity);//todo insertfailed exception 이녀석은 바로위의 엔티티랑 같은녀석이다 참조까지 같다. jpa설명에있다.
    return modelMapper.map(savedEntity, UserDetailDto.class);
  }

  public UserDetailDto updateByUserSeq(UserDetailUpdateDto userDetailUpdateDto) {

    var userDetailEntity = modelMapper.map(userDetailUpdateDto, UserDetailEntity.class);
    var updatedEntity = userDetailRepository.save(userDetailEntity);
    return modelMapper.map(updatedEntity, UserDetailDto.class);
  }

  //나는 삭제만 할거라고 알고 있다. 근데 다른 사람이 이녀석을 쓸 때, 트랜잭션 걸고할껏.
  //일단 트랜잭션공부가 더 필요.
  public long deleteByUserSeq(Long userSeq) {
    return userDetailRepository.deleteByUserSeq(userSeq);
  }

  // 완벽하게 dto 랑 맞지 않으니까.왜? default 로 생성되는 db설정들 외 기타 요인들 떄문.
  // 업데이트 후 업데이트 된 entitiy 를 다시 dto로 만들어서 보내준다.


}
