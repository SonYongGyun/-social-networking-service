package kr.co.mz.sns.entity.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

    public FriendRelationshipEntity requesterEntity(UserEntity user) {
        this.userEntity = user;
        return this;
    }

    public FriendRelationshipEntity responderEntity(UserEntity friend) {
        this.friendEntity = friend;
        return this;
    }


}
