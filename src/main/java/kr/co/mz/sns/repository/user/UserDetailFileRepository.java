package kr.co.mz.sns.repository.user;

import kr.co.mz.sns.entity.user.UserDetailFileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDetailFileRepository extends JpaRepository<UserDetailFileEntity, Long> {

  UserDetailFileEntity findByName(String name);

}
