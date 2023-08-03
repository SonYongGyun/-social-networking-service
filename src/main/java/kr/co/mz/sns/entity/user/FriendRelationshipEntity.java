package kr.co.mz.sns.entity.user;

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
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Data
@Entity
@NoArgsConstructor
@Table(name = "friend_relationship")
@EntityListeners(AuditingEntityListener.class)
public class FriendRelationshipEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "seq")
  private Long seq;

  @Column(name = "status", nullable = false)
  private String status;

  @CreatedDate
  @Column(name = "created_at", nullable = false)
  private LocalDateTime createdAt;

  @LastModifiedDate
  @Column(name = "modified_at")
  private LocalDateTime modifiedAt;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_seq")
  private UserEntity userEntity;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "friend_seq")
  private UserEntity friendEntity;

  public FriendRelationshipEntity userEntity(UserEntity user) {
    this.userEntity = user;
    return this;
  }

  public FriendRelationshipEntity friendEntity(UserEntity friend) {
    this.friendEntity = friend;
    return this;
  }

  public FriendRelationshipEntity status(String status) {
    this.status = status;
    return this;
  }


}
