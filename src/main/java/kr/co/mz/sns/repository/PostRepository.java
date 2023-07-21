package kr.co.mz.sns.repository;

import java.util.List;
import kr.co.mz.sns.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<PostEntity, Integer> {

  List<PostEntity> findAll();
}
