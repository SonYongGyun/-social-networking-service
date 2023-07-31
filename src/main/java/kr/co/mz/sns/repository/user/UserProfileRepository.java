package kr.co.mz.sns.repository.user;

import java.util.List;
import kr.co.mz.sns.entity.user.UserProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface UserProfileRepository extends JpaRepository<UserProfileEntity, Long> {

  UserProfileEntity findByName(String name);

  @Modifying
  @Transactional
  UserProfileEntity deleteBySeq(Long fileSeq);

  List<UserProfileEntity> findAllByUserSeq(Long userSeq);

  @Modifying
  @Transactional
  @Query("DELETE FROM UserProfileEntity f WHERE f.userSeq = :userSeq")
  int deleteAllByUserSeq(Long userSeq);
}
