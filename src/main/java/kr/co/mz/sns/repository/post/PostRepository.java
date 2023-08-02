package kr.co.mz.sns.repository.post;

import jakarta.validation.constraints.NotNull;
import java.util.Optional;
import kr.co.mz.sns.entity.post.PostEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostRepository extends JpaRepository<PostEntity, Long> {

    Page<PostEntity> findByContentContaining(String keyword, Pageable pageable);

    @NotNull Page<PostEntity> findAll(@NotNull Pageable pageable);

    @Query("SELECT DISTINCT p FROM PostEntity p " +
        "JOIN FETCH p.postFiles " +
        "JOIN FETCH p.comments")
    Page<PostEntity> findAllWithPostFilesAndComments(Pageable pageable);

    @Query("SELECT DISTINCT p FROM PostEntity p " +
        "JOIN FETCH p.postFiles " +
        "JOIN FETCH p.comments " +
        "WHERE p.seq = :seq")
    Optional<PostEntity> findBySeqWithPostFilesAndComments(@Param("seq") Long seq);


}
