package kr.co.mz.sns.repository.comment;

import kr.co.mz.sns.entity.comment.CommentLikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentLikeRepository extends JpaRepository<CommentLikeEntity, Long> {

}
