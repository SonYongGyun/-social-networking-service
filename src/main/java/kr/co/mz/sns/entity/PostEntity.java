package kr.co.mz.sns.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import kr.co.mz.sns.auditing.CustomAuditingEntityListener;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

@Entity
@EntityListeners(CustomAuditingEntityListener.class)
@Table(name = "Post", schema = "sns")
@Data
@NoArgsConstructor
public class PostEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "seq")
    private Long seq;
    @Column(name = "content", nullable = false)
    private String content;
    @Column(name = "likes", columnDefinition = "int DEFAULT 0")
    private Integer likes = 0;
    @CreatedDate
    @Column(name = "created_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp createdAt;
    @LastModifiedDate
    @Column(name = "modified_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private Timestamp modifiedAt;
    @CreatedBy
    @LastModifiedBy
    @ManyToOne
    @JoinColumn(name = "user_seq", nullable = false)
    private UserEntity userEntity;
    @OneToMany(mappedBy = "postEntity", cascade = CascadeType.ALL)
    private List<CommentEntity> comments = new ArrayList<>();

    public void setUsers(UserEntity userEntity) {
        this.userEntity = userEntity;
        userEntity.getPosts().add(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        var that = (PostEntity) o;
        return Objects.equals(seq, that.seq) && Objects.equals(userEntity, that.userEntity) && Objects.equals(content,
            that.content)
            && Objects.equals(likes, that.likes) && Objects.equals(createdAt, that.createdAt)
            && Objects.equals(modifiedAt, that.modifiedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(seq, content, likes, createdAt, modifiedAt, userEntity);
    }
}
