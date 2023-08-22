package kr.co.mz.sns.dto.user.detail;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class CompleteUserDetailDto {

  private Long detailSeq;
  private Long userSeq;
  private Boolean blocked;
  private String greeting;
  private LocalDateTime createdAt;
  private LocalDateTime modifiedAt;
}
