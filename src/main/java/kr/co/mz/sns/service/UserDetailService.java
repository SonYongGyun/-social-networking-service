package kr.co.mz.sns.service;

import kr.co.mz.sns.dto.UserDetailDto;
import kr.co.mz.sns.repository.UserDetailRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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

  public UserDetailDto findById(Long id) {
    return modelMapper.map(userDetailRepository.findById(id), UserDetailDto.class);
  }

}
