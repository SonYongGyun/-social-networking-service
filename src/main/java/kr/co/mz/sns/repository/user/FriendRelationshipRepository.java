package kr.co.mz.sns.repository.user;

import java.util.List;
import java.util.Optional;
import kr.co.mz.sns.entity.user.FriendRelationshipEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FriendRelationshipRepository extends JpaRepository<FriendRelationshipEntity, Long> {

  Optional<FriendRelationshipEntity> findBySeq(Long friendSeq);

  List<FriendRelationshipEntity> findAll();

  @Query("SELECT f FROM FriendRelationshipEntity f JOIN FETCH f.userDetailEntity WHERE f.seq = :seq")
  Optional<FriendRelationshipEntity> findBySeqWithUserDetail(@Param("seq") Long seq);
}

