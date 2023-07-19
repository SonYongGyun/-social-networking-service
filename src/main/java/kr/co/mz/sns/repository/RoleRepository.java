package kr.co.mz.sns.repository;

import java.util.Optional;
import kr.co.mz.sns.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<RoleEntity, Integer> {

  Optional<RoleEntity> findByName(String name);
}
