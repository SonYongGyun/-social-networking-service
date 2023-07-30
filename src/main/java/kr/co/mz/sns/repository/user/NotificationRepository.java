package kr.co.mz.sns.repository.user;

import kr.co.mz.sns.entity.comment.NotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<NotificationEntity, Long> {
  
}
