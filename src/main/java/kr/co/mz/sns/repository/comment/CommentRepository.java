package kr.co.mz.sns.repository.comment;

import java.util.List;
import java.util.Optional;
import kr.co.mz.sns.entity.comment.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface CommentRepository extends JpaRepository<CommentEntity, Long> {

    Optional<CommentEntity> findBySeq(Long seq);

    void deleteByPostSeq(Long postSeq);

    List<CommentEntity> findAllByPostSeq(Long postSeq);

    @Modifying
    @Query("delete from CommentEntity c where c.postSeq = :postSeq")
    void deleteAllByPostSeq(@Param("postSeq") Long postSeq);

    @Modifying
    @Query("delete from CommentEntity c where c.seq in :commentSeqs")
    void deleteAllByCommentSeqs(@Param("commentSeqs") List<Long> commentSeqs);
}
