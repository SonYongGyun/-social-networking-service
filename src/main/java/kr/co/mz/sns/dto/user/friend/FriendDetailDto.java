package kr.co.mz.sns.dto.user.friend;

import java.time.LocalDateTime;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FriendDetailDto {

  private Long seq;
  private Long userSeq;
  private String status;
  private LocalDateTime createdAt;
  private Long friendSeq;
  private String name;
  private String greeting;
  private LocalDateTime lastLoginAt;

}
