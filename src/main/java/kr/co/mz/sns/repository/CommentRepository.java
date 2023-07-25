package kr.co.mz.sns.repository;

import kr.co.mz.sns.dto.CommentDto;
import kr.co.mz.sns.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.xml.stream.events.Comment;
import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {

    Optional<List<CommentEntity>> findByPostSeq(Long postSeq);

    Optional<CommentEntity> findBySeq(Long seq);

}
