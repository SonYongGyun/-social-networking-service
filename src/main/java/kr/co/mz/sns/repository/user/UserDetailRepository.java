package kr.co.mz.sns.repository.user;

import java.util.Optional;
import kr.co.mz.sns.entity.user.UserDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

public interface UserDetailRepository extends JpaRepository<UserDetailEntity, Long> {

  Optional<UserDetailEntity> findByDetailSeq(Long userSeq);

//  Optional<UserDetailEntity> findByName(String userName);

  @Modifying
  @Transactional
  UserDetailEntity deleteByDetailSeq(Long userSeq);


}
