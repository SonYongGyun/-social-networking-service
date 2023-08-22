package kr.co.mz.sns.dto.user.friend;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AFriendDto {

  private Long seq;
  private Long userSeq;
  private Long friendSeq;
  private String name;
  private String greeting;
  private LocalDateTime lastLoginAt;
}
