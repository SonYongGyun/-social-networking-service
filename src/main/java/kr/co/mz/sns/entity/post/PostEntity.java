package kr.co.mz.sns.entity.post;

import jakarta.persistence.*;
import kr.co.mz.sns.converter.PostVisibilityConverter;
import kr.co.mz.sns.entity.comment.CommentEntity;
import kr.co.mz.sns.enums.PostVisibility;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Set;

@Builder
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "Post")
@Setter
@Getter
@ToString
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
    @Convert(converter = PostVisibilityConverter.class)
    @Column(name = "visibility", length = 3, nullable = false)
    private PostVisibility postVisibility;
    @OneToMany
    private Set<PostFileEntity> postFiles;
    @OneToMany
    private Set<CommentEntity> comments;
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
