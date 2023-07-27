package kr.co.mz.sns.dto.user;

import java.time.LocalDateTime;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MentionedFriendDto {

  private String name;
  private LocalDateTime lastLoginAt;
}
