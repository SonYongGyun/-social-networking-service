package kr.co.mz.sns.entity.post;

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
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "Post")
@Data
@NoArgsConstructor
public class PostEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "seq")
  private Long seq;
  @Column(name = "content", nullable = false)
  private String content;
  @Column(name = "likes", nullable = false)
  private Integer likes = 0;
  @CreatedBy
  @LastModifiedBy
  @Column(name = "create_by", nullable = false)
  private Long createBy;
  @CreatedDate
  @Column(name = "created_at", nullable = false)
  private LocalDateTime createdAt;
  @LastModifiedDate
  @Column(name = "modified_at")
  private LocalDateTime modifiedAt;
}
