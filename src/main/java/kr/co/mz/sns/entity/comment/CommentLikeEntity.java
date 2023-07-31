package kr.co.mz.sns.entity.comment;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import kr.co.mz.sns.auditing.AuditorAwareImpl;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class) //???? 두개의 차이
@Table(name = "comment_like")
@Data
@NoArgsConstructor
public class CommentLikeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "seq")
  private Long seq;
  @Column(name = "post_seq")
  private Long postSeq;
  @Column(name = "comment_seq")
  private Long commentSeq;
  @CreatedBy
  @Column(name = "user_seq")
  private Long userSeq;
  @CreatedDate
  @Column(name = "created_at", nullable = false)
  private LocalDateTime createdAt;

  public CommentLikeEntity(Long commentSeq, Long postSeq) {
    this.commentSeq = commentSeq;
    this.postSeq = postSeq;
  }
}
