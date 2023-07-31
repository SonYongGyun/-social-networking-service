package kr.co.mz.sns.dto.comment;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentLikeDto {
    private Long userSeq;
    private Long postSeq;
    private Long commentSeq;
    private LocalDateTime createdAt;

    public CommentLikeDto(Long seq, Long postSeq) {
        setCommentSeq(seq);
        setPostSeq(postSeq);
    }
}
