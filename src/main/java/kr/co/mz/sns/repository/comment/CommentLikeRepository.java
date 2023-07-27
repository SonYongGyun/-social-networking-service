package kr.co.mz.sns.repository.comment;

import kr.co.mz.sns.entity.comment.CommentLikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentLikeRepository extends JpaRepository<CommentLikeEntity, Long> {
    Optional<List<CommentLikeEntity>> findByCommentSeq(Long commentSeq);

}
