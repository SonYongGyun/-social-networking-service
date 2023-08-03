package kr.co.mz.sns.repository.user;

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
  Page<FriendRelationshipEntity> findAllByUserSeqAsStream(@Param("userSeq") Long userSeq, Pageable pageable);

  Optional<FriendRelationshipEntity> findByUserEntityAndFriendEntity(Long userSeq, Long friendSeq);

//  @Query("SELECT fr FROM FriendRelationshipEntity fr JOIN FETCH fr.userDetailEntity ud WHERE ud.name = :name")
//  List<FriendRelationshipEntity> findByFriendName(@Param("name") String name);
}

