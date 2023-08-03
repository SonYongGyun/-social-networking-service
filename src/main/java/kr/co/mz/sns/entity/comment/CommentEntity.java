package kr.co.mz.sns.entity.comment;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

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
    @OneToMany(mappedBy = "commentEntity")
    private List<CommentFileEntity> commentFiles;
    @Column(name = "post_seq")
    private Long postSeq;
    @Column(name = "comment_like", nullable = false)
    private Long commentLike;
    @CreatedDate
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    @LastModifiedDate
    @Column(name = "modified_at")
    private LocalDateTime modifiedAt;

    public CommentEntity increaseCommentLike() {
        this.commentLike += 1;
        return this;
    }

}