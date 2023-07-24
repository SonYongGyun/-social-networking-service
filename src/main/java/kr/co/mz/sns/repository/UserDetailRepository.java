package kr.co.mz.sns.repository;

import java.util.Optional;
import kr.co.mz.sns.entity.UserDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDetailRepository extends JpaRepository<UserDetailEntity, Long> {

  Optional<UserDetailEntity> findById(Long id);

  boolean existsById(Long id);

}
