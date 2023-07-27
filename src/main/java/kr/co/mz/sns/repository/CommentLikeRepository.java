package kr.co.mz.sns.repository;

import kr.co.mz.sns.entity.CommentEntity;
import kr.co.mz.sns.entity.CommentLikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentLikeRepository extends JpaRepository<CommentLikeEntity, Long> {

}
