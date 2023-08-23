package kr.co.mz.sns.user.dto.detail;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CompleteUserDetailDto {

    private Long detailSeq;
    private Long userSeq;
    private Boolean blocked;
    private String greeting;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}
