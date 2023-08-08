package kr.co.mz.sns.repository.user;

import java.util.List;
import java.util.Optional;
import kr.co.mz.sns.dto.user.detail.UserDetailAndProfileDto;
import kr.co.mz.sns.entity.user.UserDetailEntity;
import kr.co.mz.sns.entity.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface UserDetailRepository extends JpaRepository<UserDetailEntity, Long> {

  @Transactional
  Optional<UserDetailEntity> findByUserEntity_Seq(Long userSeq);

  @Transactional
  @Query("""
      select new kr.co.mz.sns.dto.user.detail.UserDetailAndProfileDto (
            d.detailSeq,
            d.userEntity.seq,
            d.blocked,
            d.greeting,
            d.createdAt,
            d.modifiedAt
      )
      from UserDetailEntity d
      """)
  Optional<UserDetailAndProfileDto> findUserDetailByUserEntity_Seq(Long userSeq);

  @Transactional
  Optional<UserDetailEntity> findByUserEntity_Name(String userName);

  @Modifying
  @Transactional
  Long deleteByUserEntity(UserEntity userEntity);

  @Query("select d from UserDetailEntity d where d.userEntity.seq in :userSeqs")
  List<UserDetailEntity> findAllByUserEntity_SeqIn(@Param("userSeqs") List<Long> userSeqs);
}
