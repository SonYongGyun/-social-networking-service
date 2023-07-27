package kr.co.mz.sns.dto.user;

import java.time.LocalDateTime;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GenericFriendDto {

  private Long seq;
  private Long userSeq;
  private String status;
  private Long friendSeq;
  private LocalDateTime createdAt;
  private LocalDateTime modifiedAt;

}
