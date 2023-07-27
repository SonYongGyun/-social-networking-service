package kr.co.mz.sns.repository.user;

import java.util.Optional;
import kr.co.mz.sns.entity.user.UserDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDetailRepository extends JpaRepository<UserDetailEntity, Long> {

  Optional<UserDetailEntity> findByUserSeq(Long userSeq);

  boolean existsByUserSeq(Long userSeq);


  long deleteByUserSeq(Long userSeq);


}
