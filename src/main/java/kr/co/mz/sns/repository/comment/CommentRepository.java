package kr.co.mz.sns.repository.comment;

import java.util.Optional;
import java.util.List;
import kr.co.mz.sns.entity.comment.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface CommentRepository extends JpaRepository<CommentEntity, Long> {

  Optional<CommentEntity> findBySeq(Long seq);

  void deleteByPostSeq(Long postSeq);

  List<CommentEntity> findAllByPostSeq(Long postSeq);

  @Query(value = "delete from commentEntity c where c.post_seq = :postSeq", nativeQuery = true)
  void deleteAllByPostSeq(@Param("postSeq") Long postSeq);

  @Query(value = "delete from commentEntity c where c.seq in :commentSeqs", nativeQuery = true)
  void deleteAllByCommentSeqs(@Param("commentSeqs") List<Long> commentSeqs);
}
