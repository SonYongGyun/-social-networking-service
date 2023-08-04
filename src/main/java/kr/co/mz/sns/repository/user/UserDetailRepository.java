package kr.co.mz.sns.repository.user;

import java.util.List;
import java.util.Optional;
import kr.co.mz.sns.entity.user.UserDetailEntity;
import kr.co.mz.sns.entity.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface UserDetailRepository extends JpaRepository<UserDetailEntity, Long> {

  Optional<UserDetailEntity> findByDetailSeq(Long detailSeq);

  //  Optional<UserDetailEntity> findByName(String userName);
  Optional<UserDetailEntity> findByUserEntity(UserEntity userEntity);

  @Modifying
  @Transactional
  Long deleteByUserEntity(UserEntity userEntity);

  @Query("select d from UserDetailEntity d where d.userEntity.seq in :userSeqs")
  List<UserDetailEntity> findAllByUserEntity_SeqIn(@Param("userSeqs") List<Long> userSeqs);
}
