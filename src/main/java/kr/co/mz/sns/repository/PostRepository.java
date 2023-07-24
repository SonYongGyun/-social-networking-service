package kr.co.mz.sns.repository;

import kr.co.mz.sns.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<PostEntity,Long> {
}
