package kr.co.mz.sns.repository;

import java.util.Optional;
import kr.co.mz.sns.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

  Optional<UserEntity> findByName(String name);

  Boolean existsByName(String name);


}
