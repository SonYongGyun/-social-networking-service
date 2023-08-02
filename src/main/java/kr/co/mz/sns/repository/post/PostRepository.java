package kr.co.mz.sns.repository.post;

import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;
import kr.co.mz.sns.entity.post.PostEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostRepository extends JpaRepository<PostEntity, Long> {

    Page<PostEntity> findByContentContaining(String keyword, Pageable pageable);
    //dto 로 받는 경우가 있는지?

    @NotNull Page<PostEntity> findAll(@NotNull Pageable pageable);

    @Query("SELECT DISTINCT p FROM PostEntity p " +
        "LEFT JOIN FETCH p.postFiles " +
        "LEFT JOIN FETCH p.comments")
    Page<PostEntity> findAllWithPostFilesAndComments(Pageable pageable);

    @Query("SELECT DISTINCT p FROM PostEntity p " +
        "LEFT JOIN FETCH p.postFiles " +
        "LEFT JOIN FETCH p.comments " +
        "WHERE p.seq = :seq")
    Optional<PostEntity> findBySeqWithPostFilesAndComments(@Param("seq") Long seq);

    @Query("select p from PostEntity p left join fetch p.postFiles left join fetch p.comments")
    List<PostEntity> fetchAll();
}
