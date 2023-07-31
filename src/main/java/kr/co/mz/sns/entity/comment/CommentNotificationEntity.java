package kr.co.mz.sns.entity.comment;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "comment_notification")
@Data
@NoArgsConstructor
public class CommentNotificationEntity {

  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  @Column(name = "seq")
  private Long seq;

  @CreatedBy
  @Column(name = "mentioner_seq")
  private Long mentionerSeq;

  @CreatedDate
  @Column(name = "mentioned_at")
  private LocalDateTime mentionedAt;

  @Column(name = "read_status")
  private Boolean readStatus;

  @Column(name = "target_seq")
  private Long targetSeq;


}
