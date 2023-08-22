package kr.co.mz.sns.repository.user;

import kr.co.mz.sns.entity.user.UserDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface UserDetailRepository extends JpaRepository<UserDetailEntity, Long> {

    Optional<UserDetailEntity> findByUserEntity_Seq(Long userSeq);

    @Transactional
    Optional<UserDetailEntity> findByUserEntity_Email(String email);

    @Query("select d from UserDetailEntity d where d.userEntity.seq in :userSeqs")
    List<UserDetailEntity> findAllByUserEntity_SeqIn(@Param("userSeqs") List<Long> userSeqs);
}
