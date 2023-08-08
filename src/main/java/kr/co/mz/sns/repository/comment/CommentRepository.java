package kr.co.mz.sns.repository.comment;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import kr.co.mz.sns.dto.comment.UsedInPostCommentDto;
import kr.co.mz.sns.entity.comment.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface CommentRepository extends JpaRepository<CommentEntity, Long> {

    Optional<CommentEntity> findBySeq(Long seq);

    List<CommentEntity> findAllByPostSeq(Long postSeq);

    @Modifying
    @Query("delete from CommentEntity c where c.postSeq = :postSeq")
    void deleteAllByPostSeq(@Param("postSeq") Long postSeq);

    @Modifying
    @Query("delete from CommentEntity c where c.seq in :commentSeqs")
    void deleteAllByCommentSeqs(@Param("commentSeqs") List<Long> commentSeqs);

    @Modifying
    @Query(
        "select new kr.co.mz.sns.dto.comment.UsedInPostCommentDto(c.seq,c.content,c.createdAt,c.modifiedAt,c.postSeq,c.createBy,"
            + "c.likes) from CommentEntity c where c.postSeq in :postSeqs")
    Stream<UsedInPostCommentDto> findAllByPostSeqs(@Param("postSeqs") List<Long> postSeqs);

//    @Query("SELECT c FROM CommentEntity c JOIN FETCH c.postEntity WHERE c.seq = :commentSeq")
//    Optional<CommentEntity> findBySeqWithPost(@Param("commentSeq") Long commentSeq);
}
