package kr.co.mz.sns.entity.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "user_detail", schema = "sns")
public class UserDetailEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "detail_seq")
  protected Long detailSeq;
  @Column(name = "blocked", nullable = false)
  protected Boolean blocked = false;
  @Column(name = "greeting")
  protected String greeting;
  @Column(name = "last_login_at")
  protected LocalDateTime lastLoginAt;
  @CreatedDate
  @Column(name = "created_at", nullable = false)
  protected LocalDateTime createdAt;
  @LastModifiedDate
  @Column(name = "modified_at", nullable = false)
  protected LocalDateTime modifiedAt;

  @MapsId
  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_seq")
  protected UserEntity userEntity;

}
