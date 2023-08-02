package kr.co.mz.sns.dto.user.friend;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MentionedFriendDto {

  private Long userSeq;
  private String name;
}
