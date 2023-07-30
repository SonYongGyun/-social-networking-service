package kr.co.mz.sns.dto.comment;

import java.time.LocalDateTime;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NotificationDto {

  private Long mentionerSeq;
  private Long targetSeq;
  private Long readStatus;
  private LocalDateTime mentionedAt;
}
