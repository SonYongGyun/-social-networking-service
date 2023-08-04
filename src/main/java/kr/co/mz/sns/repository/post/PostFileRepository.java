package kr.co.mz.sns.repository.post;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import kr.co.mz.sns.dto.post.GenericPostFileDto;
import kr.co.mz.sns.entity.post.PostFileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostFileRepository extends JpaRepository<PostFileEntity, Long> {

    List<PostFileEntity> findAllByPostSeq(Long postSeq);

    @Modifying
    @Query("DELETE FROM PostFileEntity pf WHERE pf.postSeq = :postSeq")
    void deleteAllByPostSeq(@Param("postSeq") Long postSeq);

    @Query("SELECT pf FROM PostFileEntity pf WHERE pf.postSeq = :postSeq AND pf.name = :name")
    Optional<PostFileEntity> findByPostSeqAndName(Long postSeq, String name);

    @Query("""
        SELECT new kr.co.mz.sns.dto.post.GenericPostFileDto(pf.seq,pf.name,pf.path,pf.size,pf.extension,pf.postSeq,pf.createdAt,pf.modifiedAt) 
        FROM PostFileEntity pf 
        WHERE pf.postSeq IN :postSeqs 
        AND pf.seq = (
            SELECT MIN(pf2.seq) FROM PostFileEntity pf2 WHERE pf2.postSeq = pf.postSeq
        )
        """)
    Stream<GenericPostFileDto> findAllByPostSeqs(@Param("postSeqs") List<Long> postSeqs);
}
