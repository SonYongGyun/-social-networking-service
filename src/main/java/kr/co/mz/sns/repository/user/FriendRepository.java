package kr.co.mz.sns.repository.user;

import java.util.List;
import java.util.Optional;
import kr.co.mz.sns.entity.user.FriendEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendRepository extends JpaRepository<FriendEntity, Long> {

  Optional<FriendEntity> findBySeq(Long friendSeq);

  List<FriendEntity> findAll();

}
