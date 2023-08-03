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

    @NotNull Page<PostEntity> findAll(@NotNull Pageable pageable);

    @Query("SELECT DISTINCT p FROM PostEntity p "
        + "LEFT JOIN FETCH p.postFiles pf "
        + "WHERE pf.id = ("
        + "    SELECT MIN(pf2.id) FROM PostFileEntity pf2 WHERE pf2.postSeq = p.seq) AND p.content = :keyword")
    Page<PostEntity> findByContentContaining(String keyword, Pageable pageable);

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

    // 테스트용
    @Query("SELECT p FROM PostEntity p "
        + "LEFT JOIN FETCH p.postFiles "
        + "LEFT JOIN FETCH p.comments")
    List<PostEntity> fetchAll();
}
