package kr.co.mz.sns.dto.user;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RequestedFriendDto {

  private Long userSeq;
  private Long friendSeq;
  private String status;
}
