package kr.co.mz.sns.repository.post;

import java.util.List;
import java.util.Optional;
import kr.co.mz.sns.entity.post.PostFileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostFileRepository extends JpaRepository<PostFileEntity, Long> {

    List<PostFileEntity> findAllByPostEntity_Seq(Long postSeq);

    @Modifying
    @Query("DELETE FROM PostFileEntity pf WHERE pf.postEntity.seq = :postSeq")
    void deleteAllByPostSeq(@Param("postSeq") Long postSeq);

    @Query("SELECT pf FROM PostFileEntity pf WHERE pf.postEntity.seq = :postSeq AND pf.name = :name")
    Optional<PostFileEntity> findByPostSeqAndName(Long postSeq, String name);
}
