package kr.co.mz.sns.repository.user;

import java.util.List;
import java.util.Set;
import kr.co.mz.sns.dto.user.detail.CompleteUserProfileDto;
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
  @Query("""
          select new kr.co.mz.sns.dto.user.detail.CompleteUserProfileDto(
              p.seq,
              p.userDetailEntity.userEntity.seq,
              p.uuid,
              p.name,
              p.path,
              p.size,
              p.extension,
              p.createdAt,
              p.modifiedAt
          )
          from UserProfileEntity p
          where p.userDetailEntity.userEntity.seq = :userSeq
      """)
  Set<CompleteUserProfileDto> findAllByUserDetailEntity_UserEntity_SeqAsSet(@Param("userSeq") Long userSeq);

  @Transactional
  @Query("""
          select new kr.co.mz.sns.dto.user.detail.CompleteUserProfileDto(
              p.seq,
              p.userDetailEntity.userEntity.seq,
              p.uuid,
              p.name,
              p.path,
              p.size,
              p.extension,
              p.createdAt,
              p.modifiedAt
          )
          from UserProfileEntity p
          where p.userDetailEntity.userEntity.email = :email
      """)
  Set<CompleteUserProfileDto> findAllByUserDetailEntity_UserEntity_EmailAsSet(@Param("email") String email);


  @Transactional
  @Query("select p.seq from UserProfileEntity p where p.userDetailEntity.userEntity.seq = :userSeq")
  List<Long> findAllUserProfileSeqsByUserEntity_Seq(@Param("userSeq") Long userSeq);

  @Modifying
  @Transactional
  @Query("delete from UserProfileEntity p where p.seq in :userProfileSeqs")
  int deleteAllByUserSeq(@Param("userProfileSeqs") List<Long> userProfileSeqs);
}
