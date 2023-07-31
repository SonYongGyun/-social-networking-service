package kr.co.mz.sns.repository.post;

import java.util.List;
import kr.co.mz.sns.entity.post.PostFileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostFileRepository extends JpaRepository<PostFileEntity, Long> {

    List<PostFileEntity> findAllByPostSeq(Long postSeq);

//    @Query(value = "DELETE FROM postFileEntity pfe WHERE pfe.post_seq = :postSeq", nativeQuery = true)
//    void deleteAllByPostSeq(@Param("postSeq") Long postSeq);

    @Query(value = "DELETE FROM postFileEntity pfe WHERE pfe.post_seq = :postSeq", nativeQuery = true)
    void deleteAllByPostSeq(@Param("postSeq") Long postSeq);
    
}
