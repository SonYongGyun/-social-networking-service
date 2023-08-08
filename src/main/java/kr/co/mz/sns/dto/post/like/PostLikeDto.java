package kr.co.mz.sns.dto.post.like;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostLikeDto {

    private Long userSeq;
    private Long postSeq;
    private LocalDateTime createdAt;
}