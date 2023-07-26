package kr.co.mz.sns.service;

import kr.co.mz.sns.entity.UserEntity;
import kr.co.mz.sns.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  // 순수하게 데이터만 쓸꺼면 그냥 바로 repo 들고오면된다.
  //근데 깔끔하게 처리해주는 로직은 service에 이미 구현되어 있다. 로직이 필요하다면 뭐 이거쓰는거지.
  //이건정답이 없는데 cleanarchitecture 쓰라고하는거다.
  private final UserRepository userRepository;

  @Autowired
  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public UserEntity findEntityByUserEmail(String email) {
    if (!userRepository.existsByEmail(email)) {
      throw new EmptyResultDataAccessException("Expected 1 result, but none returned.", 1);
    }
    var userEntity = userRepository.findByEmail(email);
    return userEntity.orElseThrow();
  }


}
