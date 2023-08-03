package kr.co.mz.sns.entity.comment;

import jakarta.persistence.*;
import kr.co.mz.sns.entity.post.PostEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "Comment")
@Data
@NoArgsConstructor
public class CommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seq")
    private Long seq;
    @Column(name = "content", nullable = false)
    private String content;
    @CreatedBy
    @LastModifiedBy
    @Column(name = "create_by", nullable = false)
    private Long createBy;
    @OneToMany
    @JoinColumn(name = "comment_seq", nullable = false)
    private List<CommentFileEntity> commentFiles;
    @ManyToOne
    @JoinColumn(name = "post_seq", nullable = false)
    private PostEntity postEntity;
    @Column(name = "comment_like", nullable = false)
    private Long commentLike;
    @CreatedDate
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    @LastModifiedDate
    @Column(name = "modified_at")
    private LocalDateTime modifiedAt;

    public void setPostEntity(PostEntity postEntity) {
        this.postEntity = postEntity;
        postEntity.getComments().add(this);
    }

    public CommentEntity increaseCommentLike() {
        this.commentLike += 1;
        return this;
    }

}