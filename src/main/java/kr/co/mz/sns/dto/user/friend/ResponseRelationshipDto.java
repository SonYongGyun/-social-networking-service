package kr.co.mz.sns.dto.user.friend;

import java.time.LocalDateTime;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResponseRelationshipDto {

  private Long userSeq;
  private String status;
  private Long friendSeq;
  private LocalDateTime createdAt;
  private LocalDateTime modifiedAt;
}
