package kr.co.mz.sns.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.sql.Timestamp;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "user_detail", schema = "sns")
@Data
@NoArgsConstructor
public class UserDetailEntity {

  @CreatedBy
  @LastModifiedBy
  @Id
  @Column(name = "user_seq", nullable = false)
  private Long userSeq;
  @Column(name = "blocked", nullable = true, length = 255)
  private String blocked;
  @Column(name = "greeting", nullable = true, length = 45)
  private String greeting;
  @Column(name = "profile_picture", nullable = true, length = 255)
  private String profilePicture;
  @CreatedDate
  @Column(name = "created_at", nullable = true)
  private Timestamp createdAt;
  @LastModifiedDate
  @Column(name = "modified_at", nullable = true)
  private Timestamp modifiedAt;
}
