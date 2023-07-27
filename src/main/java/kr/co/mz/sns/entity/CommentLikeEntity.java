package kr.co.mz.sns.entity;

import jakarta.persistence.*;
import kr.co.mz.sns.auditing.AuditorAwareImpl;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@EntityListeners(AuditorAwareImpl.class) //???? 두개의 차이
@Table(name = "comment_like")
@Data
@NoArgsConstructor
public class CommentLikeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seq")
    private Long seq;
    @Column(name = "post_seq")
    private Long postSeq;
    @Column(name = "comment_seq")
    private Long commentSeq;
    @Column(name = "user_seq", nullable = false)
    private Long userSeq;
}
