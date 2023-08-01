package kr.co.mz.sns.entity.user;

import static kr.co.mz.sns.entity.user.constant.FriendRelationshipConst.FR_WAITING_PERMIT_REQUEST;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "friend_relationship")
@Data
@NoArgsConstructor
public class FriendRelationshipEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "seq")
  private Long seq;

  @Column(name = "status", nullable = false)
  private String status;

  @CreatedBy
  @Column(name = "user_seq", nullable = false)
  private Long userSeq;

//  @Column(name = "friend_seq", nullable = false)
//  private Long friendSeq;

  @CreatedDate
  @Column(name = "created_at", nullable = false)
  private LocalDateTime createdAt;

  @LastModifiedDate
  @Column(name = "modified_at")
  private LocalDateTime modifiedAt;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "friend_seq", referencedColumnName = "user_seq")
  private UserDetailEntity userDetailEntity;


  public FriendRelationshipEntity requestedBy(Long userSeq) {
    this.userSeq = userSeq;
    this.status = FR_WAITING_PERMIT_REQUEST;
    return this;
  }
}
