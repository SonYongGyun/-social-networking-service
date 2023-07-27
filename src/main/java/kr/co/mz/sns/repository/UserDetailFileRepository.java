package kr.co.mz.sns.repository;

import kr.co.mz.sns.entity.UserDetailFileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDetailFileRepository extends JpaRepository<UserDetailFileEntity, Long> {

    UserDetailFileEntity findByName(String name);

}
