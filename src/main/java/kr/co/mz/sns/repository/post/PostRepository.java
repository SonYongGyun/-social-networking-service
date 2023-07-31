package kr.co.mz.sns.repository.post;

import jakarta.validation.constraints.NotNull;
import kr.co.mz.sns.entity.post.PostEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<PostEntity, Long> {

    Page<PostEntity> findByContentContaining(String keyword, Pageable pageable);

    @NotNull Page<PostEntity> findAll(@NotNull Pageable pageable);

}
