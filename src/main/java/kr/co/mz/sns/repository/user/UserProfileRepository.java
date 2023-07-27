package kr.co.mz.sns.repository.user;

import kr.co.mz.sns.entity.user.UserProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserProfileRepository extends JpaRepository<UserProfileEntity, Long> {

  UserProfileEntity findByName(String name);

  int deleteBySeq(Long fileSeq);
}
