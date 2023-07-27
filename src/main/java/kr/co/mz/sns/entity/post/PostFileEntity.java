package kr.co.mz.sns.entity.post;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "post_file")
@Data
@NoArgsConstructor
public class PostFileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seq")
    private Long seq;
    @Column(name = "post_seq", nullable = false)
    private Long post_seq;
    @Column(name = "uuid", nullable = false)
    private String uuid = UUID.randomUUID().toString();
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "path", nullable = false)
    private String path;
    @Column(name = "size", nullable = false)
    private Long size;
    @Column(name = "extension", nullable = false)
    private String extension;
    @CreatedDate
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    @LastModifiedDate
    @Column(name = "modified_At", nullable = false)
    private LocalDateTime modifiedAt;

}
