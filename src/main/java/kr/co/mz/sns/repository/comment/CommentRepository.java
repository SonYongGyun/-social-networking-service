package kr.co.mz.sns.repository.comment;

import kr.co.mz.sns.entity.comment.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface CommentRepository extends JpaRepository<CommentEntity, Long> {

    Optional<CommentEntity> findBySeq(Long seq);

    List<CommentEntity> findAllByPostEntity_Seq(Long postSeq);

    @Modifying
    @Query("delete from CommentEntity c where c.postEntity.seq = :postSeq")
    void deleteAllByPostSeq(@Param("postSeq") Long postSeq);

    @Modifying
    @Query("delete from CommentEntity c where c.seq in :commentSeqs")
    void deleteAllByCommentSeqs(@Param("commentSeqs") List<Long> commentSeqs);

    @Query("SELECT c FROM CommentEntity c JOIN FETCH c.postEntity WHERE c.seq = :commentSeq")
    Optional<CommentEntity> findBySeqWithPost(@Param("commentSeq") Long commentSeq);

    @Query("SELECT ")
    Optional<CommentEntity> findAllCommentAndFile(Long postSeq);
}
