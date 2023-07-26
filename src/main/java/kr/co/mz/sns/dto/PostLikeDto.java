package kr.co.mz.sns.dto;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class PostLikeDto {

    private Long seq;

    private Long userSeq;

    private Long postSeq;

    private LocalDateTime createdAt;
}