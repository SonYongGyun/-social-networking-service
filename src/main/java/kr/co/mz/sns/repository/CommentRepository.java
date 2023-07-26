package kr.co.mz.sns.repository;

import java.util.Optional;
import kr.co.mz.sns.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {

//    Optional<List<CommentEntity>> findByPostEntityPostSeq(Long postSeq);

    Optional<CommentEntity> findBySeq(Long seq);
}
