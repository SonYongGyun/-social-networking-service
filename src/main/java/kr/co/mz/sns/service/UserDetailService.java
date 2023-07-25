package kr.co.mz.sns.service;

import jakarta.transaction.Transactional;
import kr.co.mz.sns.dto.UserDetailDto;
import kr.co.mz.sns.entity.UserDetailEntity;
import kr.co.mz.sns.repository.UserDetailRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

@Repository
public class UserDetailService {

  private final UserDetailRepository userDetailRepository;
  private final ModelMapper modelMapper;

  @Autowired
  public UserDetailService(UserDetailRepository userDetailRepository, ModelMapper modelMapper) {
    this.userDetailRepository = userDetailRepository;
    this.modelMapper = modelMapper;

  }

  public UserDetailDto findByUserSeq(Long userSeq) {
    if (!userDetailRepository.existsByUserSeq(userSeq)) {
      throw new EmptyResultDataAccessException("Expected 1 result, but none returned.", 1);
    }
    return modelMapper.map(userDetailRepository.findById(userSeq).orElseGet(UserDetailEntity::new),
        UserDetailDto.class);
  }

//  public UserDetailDto findByEmail(String email) {
//
//    if (!userDetailRepository.existsByEmail(userSeq)) {
//      throw new EmptyResultDataAccessException("Expected 1 result, but none returned.", 1);
//    }
//    return modelMapper.map(userDetailRepository.findById(userSeq).orElseGet(UserDetailEntity::new),
//        UserDetailDto.class);
//  }


  public UserDetailDto saveOne(UserDetailDto userDetailDto) {
    var userSeq = userDetailDto.getUserSeq();
    if (userDetailRepository.existsByUserSeq(userSeq)) {
      throw new DataIntegrityViolationException("UserDetail with user_seq " + userSeq + " already exists.");
    }
    var userDetailEntity = modelMapper.map(userDetailDto, UserDetailEntity.class);
    var savedEntity = userDetailRepository.save(userDetailEntity);//todo insertfailed exception
    return modelMapper.map(savedEntity, UserDetailDto.class);
  }

  public UserDetailDto updateByUserSeq(UserDetailDto userDetailDto) {
    if (!userDetailRepository.existsByUserSeq(userDetailDto.getUserSeq())) {
      throw new EmptyResultDataAccessException("Expected 1 result, but none returned.", 1);
    }
    var userDetailEntity = modelMapper.map(userDetailDto, UserDetailEntity.class);
    var updatedEntity = userDetailRepository.save(userDetailEntity);
    return modelMapper.map(updatedEntity, UserDetailDto.class);
  }

  @Transactional
  public long deleteByUserSeq(Long userSeq) {
    return userDetailRepository.deleteByUserSeq(userSeq);
  }

  // 완벽하게 dto 랑 맞지 않으니까.왜? default 로 생성되는 db설정들 외 기타 요인들 떄문.
  // 업데이트 후 업데이트 된 entitiy 를 다시 dto로 만들어서 보내준다.


}
