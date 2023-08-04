package kr.co.mz.sns.repository.user;

import java.util.List;
import java.util.Optional;
import kr.co.mz.sns.entity.user.FriendRelationshipEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FriendRelationshipRepository extends JpaRepository<FriendRelationshipEntity, Long> {

  Optional<FriendRelationshipEntity> findBySeq(Long friendSeq);

  @Query("SELECT f FROM FriendRelationshipEntity f JOIN FETCH f.userEntity u WHERE u.seq = :userSeq")
  Page<FriendRelationshipEntity> findAllByUserSeqAsPage(@Param("userSeq") Long userSeq, Pageable pageable);

  Optional<FriendRelationshipEntity> findByUserEntity_SeqAndFriendEntity_Seq(Long userSeq, Long friendSeq);

//  @Query("SELECT f FROM FriendRelationshipEntity f WHERE f.userEntity.seq IN :userSeqs")
//  List<FriendRelationshipEntity> findAllByUserSeqIn(@Param("userSeqs") List<Long> userSeqs, Pageable pageable);

  @Query("select f from FriendRelationshipEntity f where f.userEntity.seq in :userSeqs")
  List<FriendRelationshipEntity> findAllByUserEntity_SeqIn(@Param("userSeqs") List<Long> userSeqs, Pageable pageable);
}

