package kr.co.mz.sns.repository.comment;

import java.util.Optional;
import kr.co.mz.sns.entity.comment.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CommentRepository extends JpaRepository<CommentEntity, Long> {

  Optional<CommentEntity> findBySeq(Long seq);


}
