package kr.co.mz.sns.entity.user;

import jakarta.persistence.*;
import kr.co.mz.sns.entity.friend.FriendRelationshipEntity;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user", schema = "sns")
@EntityListeners(AuditingEntityListener.class)
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seq")
    protected Long seq;
    @Column(name = "email", nullable = false)
    protected String email;
    @Column(name = "name", nullable = false)
    protected String name;
    @Column(name = "password", nullable = false)
    protected String password;
    @Column(name = "role", nullable = false)
    protected String role;
    @CreatedDate
    @Column(name = "created_at", nullable = false)
    protected LocalDateTime createdAt;
    @LastModifiedDate
    @Column(name = "modified_at")
    protected LocalDateTime modifiedAt;

    @OneToOne(mappedBy = "userEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    protected UserDetailEntity userDetail;

    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    protected List<FriendRelationshipEntity> friendRelationships;
}
