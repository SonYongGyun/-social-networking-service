package kr.co.mz.sns.repository.comment;

import kr.co.mz.sns.entity.comment.CommentNotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentNotificationRepository extends JpaRepository<CommentNotificationEntity, Long> {

}
