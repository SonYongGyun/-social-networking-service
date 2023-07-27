package kr.co.mz.sns.repository.post;

import java.util.List;
import kr.co.mz.sns.entity.post.PostLikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostLikeRepository extends JpaRepository<PostLikeEntity, Long> {

  List<PostLikeEntity> findByPostSeq(Long postSeq);
}
