package kr.co.mz.sns.repository.comment;

import kr.co.mz.sns.entity.comment.CommentFileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentFileRepository extends JpaRepository<CommentFileEntity, Long> {
    void deleteByCommentSeq(Long commentSeq);


}
