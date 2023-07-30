package kr.co.mz.sns.repository.user;

import java.util.List;
import java.util.Optional;
import kr.co.mz.sns.entity.user.FriendRelationshipEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendRepository extends JpaRepository<FriendRelationshipEntity, Long> {

  Optional<FriendRelationshipEntity> findBySeq(Long friendSeq);

  List<FriendRelationshipEntity> findAll();

}
