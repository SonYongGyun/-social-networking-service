package kr.co.mz.sns.dto.user.friend;

import java.time.LocalDateTime;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ListFriendDto {

  private Long seq;
  private String name;
  private String status;
  private String greeting;
  private LocalDateTime createdAt;
}
