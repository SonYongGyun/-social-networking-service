package kr.co.mz.sns.repository.post;

import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;
import kr.co.mz.sns.dto.post.FindAllPostDto;
import kr.co.mz.sns.dto.post.PostAndPostFileAndCommentDto;
import kr.co.mz.sns.entity.post.PostEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostRepository extends JpaRepository<PostEntity, Long> {

    @Query("""
        SELECT new kr.co.mz.sns.dto.post.FindAllPostDto(p.seq, p.content, p.likes, p.createdAt, p.modifiedAt, p.createBy) 
        FROM PostEntity p""")
    @NotNull Page<FindAllPostDto> findAllWithPaging(@NotNull Pageable pageable);


    @Query("""
        SELECT new kr.co.mz.sns.dto.post.FindAllPostDto(p.seq, p.content, p.likes, p.createdAt, p.modifiedAt, p.createBy)
        FROM PostEntity p
        WHERE p.content LIKE %:keyword%
        """)
    Page<FindAllPostDto> findByContentContaining(String keyword, Pageable pageable);

    @Query("SELECT p FROM PostEntity p "
        + "LEFT JOIN FETCH p.postFiles pf "
        + "WHERE pf.id = ("
        + "    SELECT MIN(pf2.id) FROM PostFileEntity pf2 WHERE pf2.postSeq = p.seq)")
    Page<PostEntity> findAllWithPostFilesAndComments(Pageable pageable);


    @Query("SELECT DISTINCT p FROM PostEntity p "
        + "LEFT JOIN FETCH p.postFiles "
        + "LEFT JOIN FETCH p.comments "
        + "WHERE p.seq = :seq ")
    Optional<PostEntity> findBySeqWithPostFilesAndComments(@Param("seq") Long seq);

    @Query("""
        select
        	p.seq as postSeq, p.content as postContent, p.likes as postLikes, p.createBy as postCreateBy, p.createdAt as postCreatedAt, p.modifiedAt as postModifiedAt,
        	pf.seq as fileSeq ,pf.name as fileName ,pf.path as filePath ,pf.size as fileSize , pf.extension as fileExtension,
        	c.seq as commentSeq, c.content as commentContent, c.likes
        from PostEntity p
        left join PostFileEntity pf on pf.postSeq  = p.seq
        left join CommentEntity c on c.postSeq = p.seq
        """)
    List<PostAndPostFileAndCommentDto> fetchAll();

    @Query("""
        SELECT p FROM PostEntity p LEFT JOIN FETCH PostFileEntity pf ON p.seq = pf.postSeq LEFT JOIN FETCH CommentEntity ce 
        ON p.seq = ce.postSeq
        """)
    List<PostEntity> fetchPostAll();
}
