package kr.co.mz.sns.repository.user;

import java.util.List;
import kr.co.mz.sns.entity.user.UserProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface UserProfileRepository extends JpaRepository<UserProfileEntity, Long> {

  UserProfileEntity findByName(String name);

  @Modifying
  @Transactional
  UserProfileEntity deleteBySeq(Long fileSeq);

  @Transactional
  List<UserProfileEntity> findAllByUserDetailEntity_UserEntity_Seq(Long userSeq);


  @Transactional
  @Query("select p.seq from UserProfileEntity p where p.userDetailEntity.userEntity.seq = :userSeq")
  List<Long> findAllUserProfileSeqsByUserEntity_Seq(@Param("userSeq") Long userSeq);

  @Modifying
  @Transactional
  @Query("delete from UserProfileEntity p where p.seq in :userProfileSeqs")
  int deleteAllByUserSeq(@Param("userProfileSeqs") List<Long> userProfileSeqs);
}
